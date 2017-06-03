package com.projectnine.logic;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
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
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            
            byte[] buffer = new byte[65530];
            int read;
            System.out.println("DUPA:");
            String tmp = "";
            while((read = input.read(buffer)) != -1) {
            	tmp = convertStreamToString(input);
            };
            System.out.println("LEN:"+(tmp.length()*0.001));
            reader.close();

            
                        
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
