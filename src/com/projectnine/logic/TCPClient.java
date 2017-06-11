package com.projectnine.logic;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Observable;

public class TCPClient extends Observable implements Runnable, IServerStopper {

	private final InetAddress address;
	private final int port;
	private int byteSize;
	private boolean flag;
	
	public int getByteSize() {
		return byteSize;
	}
	
	public void setByteSize(int byteSize) {
		this.byteSize = byteSize;
	}
	public TCPClient(InetAddress address, int port, int byteSize) {
		super();
		this.address = address;
		this.port = port;
		this.byteSize = byteSize;

	}
	private String convertByteToString() {
        
        byte b = 7;
        byte[] arr = new byte[byteSize];
        
        for(int i = 0; i < byteSize; i++){
        	arr[i] = b;
        }
       return new String(arr);
        
    }
	@Override
	public void run() {
		flag = false;
		String sentence = convertByteToString()+"\n";
		Socket clientSocket = null;
		try {
			clientSocket = new Socket(address, port);
//			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
			OutputStream strOut = clientSocket.getOutputStream();
			strOut.write(String.valueOf("SIZE:"+byteSize).getBytes());
			strOut.flush();
			while(!flag){
				Thread.sleep(2);
				strOut.write(sentence.getBytes());
				strOut.flush();
			}
			strOut.close();
			clientSocket.close();
			
		} catch (IOException  | InterruptedException e) {
//			setChanged();
//			notifyObservers(e);
			e.printStackTrace();
		}
	}

	@Override
	public boolean shouldStop() {
		this.flag = true;
		return false;
	}

}
