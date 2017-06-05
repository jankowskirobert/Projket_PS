package com.projectnine.logic;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class UDPDriver implements Runnable, TransferActualizationSubject {

    private ITransferActualizer actualizer;
    private int serverPort;

    public UDPDriver(int arg) {
        this.serverPort = arg;
    }

    public UDPDriver() {
        this.serverPort = 7777;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    @Override
    public void run() {
        DatagramSocket serverSocket = null;
        try {
            serverSocket = new DatagramSocket(serverPort);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];
        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            try {
                serverSocket.receive(receivePacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String sentence = new String(receivePacket.getData());
            System.out.println("RECEIVED: " + new String(receivePacket.getData(), StandardCharsets.UTF_8));
            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();
            String capitalizedSentence = sentence.toUpperCase();
            sendData = capitalizedSentence.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            try {
                serverSocket.send(sendPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void setActualizer(ITransferActualizer actualizer) {
        this.actualizer = actualizer;
    }

}
