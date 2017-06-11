package com.projectnine.logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPClient implements Runnable, IServerStopper {
	private final InetAddress ipAddress;
	private final int port;
	private int byteSize = 0;
	private boolean flag;

	public UDPClient(InetAddress ipAddress, int port) {
		super();
		this.ipAddress = ipAddress;
		this.port = port;
	}

	public UDPClient(InetAddress ipAddress, int port, int byteSize) {
		super();
		this.ipAddress = ipAddress;
		this.port = port;
		this.byteSize = byteSize;
	}

	private String convertByteToString() {

		byte b = 7;
		byte[] arr = new byte[byteSize];

		for (int i = 0; i < byteSize; i++) {
			arr[i] = b;
		}
		return new String(arr);

	}

	public int getByteSize() {
		return byteSize;
	}

	public void setByteSize(int byteSize) {
		this.byteSize = byteSize;
	}

	@Override
	public void run() {
		this.flag = false;
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		DatagramSocket clientSocket;
		try {
			clientSocket = new DatagramSocket();
			byte[] sendData = new byte[1024];
			String sentence = convertByteToString();
			sendData = sentence.getBytes();
			String initialData= "SIZE:"+byteSize;
			DatagramPacket sendPacketInitial = new DatagramPacket(initialData.getBytes(), initialData.getBytes().length, ipAddress, port);
			clientSocket.send(sendPacketInitial);
			while(!flag){
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, port);
				clientSocket.send(sendPacket);
			}
			String endData= "FINE";
			DatagramPacket sendPacketEnd = new DatagramPacket(endData.getBytes(), endData.getBytes().length, ipAddress, port);
			clientSocket.send(sendPacketEnd);
			clientSocket.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	@Override
	public boolean shouldStop() {
		this.flag = true;
		return false;
	}

}
