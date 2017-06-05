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
	protected final ITCPTransferActualizer c;
	long totalPackages = 0;
	long timeStart;
	public WorkerRunnable(Socket clientSocket, String serverText, ITCPTransferActualizer act) {
		this.clientSocket = clientSocket;
		this.serverText = serverText;
		this.c = act;

	}

	public void run() {
		try {
			InputStream inputStream = clientSocket.getInputStream();
//			Reader inputStreamReader = new InputStreamReader(inputStream);
			byte[] buffer = new byte[65536];
			int read;
			if ((read = inputStream.read(buffer)) != -1) {
				int sizePackage = inputStream.read(buffer);
				if (c != null)
					c.setSinglePackageSize(sizePackage);
			}
			buffer = new byte[65536];
			int data = inputStream.read(buffer);
			totalPackages = 0;
			this.timeStart = System.currentTimeMillis();
			while ((data = inputStream.read(buffer)) != -1) {
//				data = inputStreamReader.read();
				if (data > 0) {
					totalPackages += data;
					double totalRound = Math.round(totalPackages * 0.001);
					c.setTotalPackageSize(totalRound);
					long secs =(System.currentTimeMillis() - this.timeStart);
					c.setTotalTime(Math.round(secs*0.001));
					if(System.currentTimeMillis() % 10 == 0){
					    double speed = Math.round(totalRound / secs);
					    System.out.println(speed);
					    c.setSpeed(speed);
					}
				}
			}
			totalPackages = 0;
//			inputStreamReader.close();
			inputStream.close();
			// InputStreamReader input = new
			// InputStreamReader(clientSocket.getInputStream());
			// OutputStream output = clientSocket.getOutputStream();
			// long time = System.currentTimeMillis();
			// BufferedReader reader = new BufferedReader(new
			// InputStreamReader(clientSocket.getInputStream()));
			//
			// byte[] buffer = new byte[65530];
			// int read;
			// System.out.println("DUPA:");
			// String tmp = "";
			// if ((read = input.read(buffer)) != -1) {
			// int sizePackage = input.read(buffer);
			// if (c != null)
			// c.setSinglePackageSize(sizePackage);
			// }
			// while ((read = input.read(buffer)) != -1) {
			// tmp = convertStreamToString(input);
			// if (c != null){
			//
			// System.out.println("TEST");
			// }
			// }
			//
			// System.out.println("LEN:" +
			// (getStringFromInputStream(input).length() * 0.001));
			//
			// reader.close();
			//
			// output.close();
			// input.close();
			//
			// System.out.println("Request processed: " + time);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
