package com.projectnine.logic;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

public class WorkerRunnable implements Runnable {

	protected Socket clientSocket = null;
    protected String serverText   = null;
    protected AtomicInteger c = null;

    public WorkerRunnable(Socket clientSocket, String serverText,AtomicInteger c) {
        this.clientSocket = clientSocket;
        this.serverText   = serverText;
        this.c = c;
    }

    public void run() {
        try {
            InputStream input  = clientSocket.getInputStream();
            OutputStream output = clientSocket.getOutputStream();
            long time = System.currentTimeMillis();
//            System.out.println(convertStreamToString(input));
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            int nRead;
            byte[] data = new byte[16384];

            while ((nRead = input.read(data, 0, data.length)) != -1) {
              buffer.write(data, 0, nRead);
              System.out.println("Data: "+ nRead + " " + clientSocket.hashCode());
            }
            buffer.flush();
            
            output.close();
            input.close();
            c.decrementAndGet();
            System.out.println("Request processed: " + time);
			
        } catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        }
    }
    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

}
