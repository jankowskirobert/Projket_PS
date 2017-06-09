package com.projectnine.logic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class TCPDriver implements Runnable, TransferActualizationSubject {

    protected int serverPort = 6660;
    protected ServerSocket serverSocket = null;
    protected boolean isStopped = false;
    protected Thread runningThread = null;
    protected Object locker = new Object();
    private final AtomicInteger c = new AtomicInteger(0);
    ExecutorService executor = Executors.newFixedThreadPool(1);
    final ReentrantLock rl = new ReentrantLock();

    private ITransferActualizer actualizer;
    private int totalPackages;
    private long timeStart;

    public TCPDriver(int port) {
        this.serverPort = port;
    }

    public TCPDriver() {
        this.serverPort = 7777;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public void run() {

        openServerSocket();
        try {
            while (true) {
                Socket socket = serverSocket.accept();
                serverSocket.close();
                System.out.println("Connection accepted: " + socket);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),
                        true);
                InputStream inputStream = socket.getInputStream();
                totalPackages = 0;
                byte[] buffer = new byte[65536];
                int read;
                int sizePackage = 0;
                if ((read = inputStream.read(buffer)) != -1) {
                    sizePackage = inputStream.read(buffer);
                    if (c != null)
                        actualizer.setSinglePackageSize(sizePackage);
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
                            actualizer.setTotalPackageSize(totalRound);
                            long secs = (System.currentTimeMillis() - this.timeStart);
                            actualizer.setTotalTime(Math.round(secs * 0.001));
                            if (System.currentTimeMillis() % 10 == 0) {
                                double speed = totalRound / secs;
                                actualizer.setSpeed((double) Math.round(speed * 100000d) / 100000d);
                            }
                        }
                    }
                    totalPackages = 0;
                    inputStream.close();
                }
                openServerSocket();
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        actualizer.updateStatus(ServerStatus.STOPPED);
        System.out.println("Server Stopped.");

    }

    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop() {
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
            actualizer.updateStatus(ServerStatus.RUNNING);
        } catch (IOException e) {
            actualizer.updateStatus(ServerStatus.FAILED);
            throw new RuntimeException("Cannot open port 8080", e);
        }
    }

    @Override
    public void setActualizer(ITransferActualizer actualizer) {
        this.actualizer = actualizer;

    }
}
