package com.projectnine.logic;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class UDPDriver implements Runnable, TransferActualizationSubject {
	private final static Logger logger =  Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
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
            logger.info("UDP Started");
        } catch (SocketException e) {
            e.printStackTrace();
        }
        byte[] receiveData = new byte[65350];

        logger.info("UDP Listening");
        while (true) {
        	
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            try {
                serverSocket.receive(receivePacket);
                String str = new String(receiveData, "UTF-8");
                if(str.contains("SIZE")){
                	String[] arry = str.split(":");
                	arry[1] = arry[1].replaceAll("\\D+","");
                	logger.info("UDP: New Client, package size:" + arry[1]);
                	System.out.println("UDP: New Client, package size:" + arry[1]);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            String sentence = new String(receivePacket.getData());
//            System.out.println("RECEIVED: " + new String(receivePacket.getData(), StandardCharsets.UTF_8));
            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();
            String capitalizedSentence = sentence.toUpperCase();
//
//            try {
////                serverSocket.send(sendPacket);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }

    }

    @Override
    public void setActualizer(ITransferActualizer actualizer) {
        this.actualizer = actualizer;
    }

}
