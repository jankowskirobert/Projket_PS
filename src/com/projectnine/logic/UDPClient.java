package com.projectnine.logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.Observable;
import java.util.logging.Logger;

public class UDPClient extends Observable implements Runnable, IServerStopper {
	private InetAddress ipAddress;
	private int port;
	private int byteSize = 0;
	private boolean flag;
	private final static Logger logger =  Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	
    public InetAddress getIpAddress() {
        return ipAddress;
    }

    
    public void setIpAddress(InetAddress ipAddress) {
        this.ipAddress = ipAddress;
    }

    
    public int getPort() {
        return port;
    }

    
    public void setPort(int port) {
        this.port = port;
    }

    public UDPClient() {
        super();
    }

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

	private byte[] prepareMatrix(){
	    byte b = 7;
        byte[] arr = new byte[byteSize];

        for (int i = 0; i < byteSize; i++) {
            arr[i] = b;
        }

	    return arr;
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
		DatagramSocket clientSocket;
		logger.info("UDP Client Started");
		try {
			clientSocket = new DatagramSocket();
			byte[] sendData = new byte[1024];
			String sentence = convertByteToString();
			clientSocket.setSendBufferSize(byteSize);
			sendData = sentence.getBytes();
			String initialData= "SIZE:"+byteSize;
			DatagramPacket sendPacketInitial = new DatagramPacket(initialData.getBytes(), initialData.getBytes().length, ipAddress, port);
			logger.info("UDP Client Send Initial Package: "+initialData);
			clientSocket.send(sendPacketInitial);
			byte[] dataToSend = prepareMatrix(); 
			while(!flag){
				DatagramPacket sendPacket = new DatagramPacket(dataToSend, dataToSend.length, ipAddress, port);
				clientSocket.send(sendPacket);
			}
			String endData= "FINE";
			DatagramPacket sendPacketEnd = new DatagramPacket(endData.getBytes(), endData.getBytes().length, ipAddress, port);
			clientSocket.send(sendPacketEnd);
			clientSocket.close();
			
		} catch (IOException e) {
		    setChanged();
		    notifyObservers(e);
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
