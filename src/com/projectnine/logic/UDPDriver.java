package com.projectnine.logic;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Observable;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class UDPDriver extends Observable implements Runnable, TransferActualizationSubject {

    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private ITransferActualizer actualizer;
    private int serverPort;
    private boolean serverWorks = false;
    
    public boolean isServerWorks() {
        return serverWorks;
    }

    
    public void setServerWorks(boolean serverWorks) {
        this.serverWorks = serverWorks;
    }

    public UDPDriver(int arg) {
        this.serverPort = arg;
    }

    public UDPDriver() {
        this.serverPort = 7777;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    private long timeStart = 0;

    @Override
    public void run() {
        actualizer.updateStatus(ServerStatus.RUNNING);
        serverWorks = false;
        DatagramSocket serverSocket = null;
        try {
            serverSocket = new DatagramSocket(serverPort);
            logger.info("UDP Started");
        } catch (SocketException e) {
            e.printStackTrace();
            setChanged();
            notifyObservers(e);
        }

        // byte[] receiveData = new byte[65350];
        logger.info("UDP Listening");
        int oldSingle = 0;
        int totalPackages = 0;
        long totalTime = System.currentTimeMillis();
        DecimalFormat f = new DecimalFormat("##.00");
        timeStart = System.currentTimeMillis();
        while (!serverWorks) {
            
            byte[] receiveData = new byte[65350];
            DatagramPacket receivePacket = null;
            String str = null;

            try {
                receivePacket = new DatagramPacket(receiveData, receiveData.length);
                
                serverSocket.receive(receivePacket);
                
                str = new String(receiveData, "UTF-8");

                receiveData = new byte[receivePacket.getLength()];
            } catch (IOException e) {
                actualizer.updateStatus(ServerStatus.FAILED);
                e.printStackTrace();
            }

            if (str.contains("SIZE")) {
                String[] arry = str.split(":");
                arry[1] = arry[1].replaceAll("\\D+", "");
                logger.info("UDP: New Client, package size:" + arry[1]);
                System.out.println("UDP: New Client, package size:" + arry[1]);
                oldSingle = Integer.parseInt(arry[1]);
                actualizer.setSinglePackageSize(oldSingle);
            }
            if (oldSingle != receivePacket.getLength()) {
                oldSingle = receivePacket.getLength();
                timeStart = System.currentTimeMillis();
                totalPackages = 0;
                actualizer.setSinglePackageSize(oldSingle);
            }
            totalPackages += receivePacket.getLength();
            try {
                actualizer.setTotalPackageSize(f.parse(f.format(totalPackages * 0.001)).doubleValue());

                long estimatedTime = System.currentTimeMillis() - timeStart;

                actualizer.setTotalTime(f.parse(f.format(TimeUnit.MILLISECONDS.toSeconds(estimatedTime))).longValue());

                actualizer.setSpeed(f.parse(f.format((totalPackages * 0.001) / (estimatedTime * 0.001))).doubleValue());
            } catch (ParseException e) {
                actualizer.updateStatus(ServerStatus.FAILED);
                e.printStackTrace();
            }

//            actualizer.setTotalPackageSize(totalPackages * 0.001);

        }
        actualizer.updateStatus(ServerStatus.STOPPED);
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
