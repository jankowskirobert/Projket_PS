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
            serverSocket = new DatagramSocket( serverPort);
            logger.info("UDP Started");
        } catch (SocketException e) {
            e.printStackTrace();
        }
       
//    	byte[] receiveData = new byte[65350];
        logger.info("UDP Listening");
        int oldSingle = 0;
        while (true) {
        	
        	byte[] receiveData = new byte[65350];
            DatagramPacket receivePacket = null;
            String str = null;
            try {
            	receivePacket = new DatagramPacket(receiveData, receiveData.length);
            	
                serverSocket.receive(receivePacket);
                str = new String(receiveData, "UTF-8");
                System.out.println(str);
                receiveData = new byte[receivePacket.getLength()];
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            if(str.contains("SIZE")){
            	String[] arry = str.split(":");
            	arry[1] = arry[1].replaceAll("\\D+","");
            	logger.info("UDP: New Client, package size:" + arry[1]);
            	System.out.println("UDP: New Client, package size:" + arry[1]);
            	oldSingle = Integer.parseInt(arry[1]);
            	actualizer.setSinglePackageSize(oldSingle);
            }
//            if(str.contains("FINE") || receivePacket.getLength() == 10){
//            	logger.info("UDP: Client Disconnected");
//            	System.out.println("UDP: Client Disconnected");
////            	actualizer.setSinglePackageSize(Integer.parseInt(arry[1]));
//            }
            if(oldSingle != receivePacket.getLength()){
            	oldSingle = receivePacket.getLength();
            	actualizer.setSinglePackageSize(oldSingle);
            }
            String sentence = new String(receivePacket.getData(), StandardCharsets.UTF_8);
            System.out.println("RECEIVED: " + /*new String(receivePacket.getData(), StandardCharsets.UTF_8) +*/ + receivePacket.getLength());
            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();
            String capitalizedSentence = sentence.toUpperCase();
//            serverSocket.se
//
//            try {
////                serverSocket.send(sendPacket);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }

    }

    private boolean byteArrayCheck12(final byte[] array) {
        int sum = 0;
        for (byte b : array) {
            sum |= b;
        }
        return (sum == 0);
    }
    
    @Override
    public void setActualizer(ITransferActualizer actualizer) {
        this.actualizer = actualizer;
    }

}
