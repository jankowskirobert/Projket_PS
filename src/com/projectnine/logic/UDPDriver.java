package com.projectnine.logic;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class UDPDriver implements Runnable {

	@Override
	public void run() {
		DatagramSocket serverSocket = null;
		try {
			serverSocket = new DatagramSocket(6660);
		} catch (SocketException e) {
			e.printStackTrace();
		}
        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];
        while(true)
           {
              DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
              try {
				serverSocket.receive(receivePacket);
			} catch (IOException e) {
				e.printStackTrace();
			}
              String sentence = new String( receivePacket.getData());
//              System.out.println("RECEIVED: " + new String(receivePacket.getData(), StandardCharsets.UTF_8));
              InetAddress IPAddress = receivePacket.getAddress();
              int port = receivePacket.getPort();
              String capitalizedSentence = sentence.toUpperCase();
              sendData = capitalizedSentence.getBytes();
              DatagramPacket sendPacket =
              new DatagramPacket(sendData, sendData.length, IPAddress, port);
              try {
				serverSocket.send(sendPacket);
			} catch (IOException e) {
				e.printStackTrace();
			}
           }

	}

}
