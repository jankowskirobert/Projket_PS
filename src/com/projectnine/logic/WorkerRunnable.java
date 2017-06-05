package com.projectnine.logic;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

public class WorkerRunnable implements Runnable {

    protected Socket clientSocket = null;
    protected String serverText = null;
    protected final ITransferActualizer c;
    long totalPackages = 0;
    long timeStart;

    public WorkerRunnable(Socket clientSocket, String serverText, ITransferActualizer act) {
        this.clientSocket = clientSocket;
        this.serverText = serverText;
        this.c = act;

    }

    public void run() {
        try {
            InputStream inputStream = clientSocket.getInputStream();
            byte[] buffer = new byte[65536];
            int read;
            int sizePackage = 0;
            if ((read = inputStream.read(buffer)) != -1) {
                sizePackage = inputStream.read(buffer);
                if (c != null)
                    c.setSinglePackageSize(sizePackage);
            }
            if (sizePackage > 0) {
                buffer = new byte[sizePackage];
                int data = inputStream.read(buffer);
                totalPackages = 0;
                this.timeStart = System.currentTimeMillis();
                while ((data = inputStream.read(buffer)) != -1) {
                    // data = inputStreamReader.read();
                    if (data > 0) {
                        totalPackages += data;
                        double totalRound = Math.round(totalPackages * 0.001);
                        c.setTotalPackageSize(totalRound);
                        long secs = (System.currentTimeMillis() - this.timeStart);
                        c.setTotalTime(Math.round(secs * 0.001));
                        if (System.currentTimeMillis() % 10 == 0) {
                            double speed = totalRound / secs;
                            c.setSpeed((double) Math.round(speed * 100000d) / 100000d);
                        }
                    }
                }
                totalPackages = 0;
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
