package com.projectnine.logic;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class UDPDriver implements Runnable, TransferActualizationSubject {

    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
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

    private long timeStart = 0;

    @Override
    public void run() {
        DatagramSocket serverSocket = null;
        try {
            serverSocket = new DatagramSocket(serverPort);
            logger.info("UDP Started");
        } catch (SocketException e) {
            e.printStackTrace();
        }

        // byte[] receiveData = new byte[65350];
        logger.info("UDP Listening");
        int oldSingle = 0;
        int totalPackages = 0;
        long totalTime = System.currentTimeMillis();
        DecimalFormat f = new DecimalFormat("##.00");
        timeStart = System.currentTimeMillis();
        while (true) {

            byte[] receiveData = new byte[65350];
            DatagramPacket receivePacket = null;
            String str = null;
            // System.out.println("T1");
            try {
                receivePacket = new DatagramPacket(receiveData, receiveData.length);
                
                serverSocket.receive(receivePacket);
                
                str = new String(receiveData, "UTF-8");
                // System.out.println(str);
                receiveData = new byte[receivePacket.getLength()];
            } catch (IOException e) {
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
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            // double totalRound = Math.round(totalPackages * 0.001);
            // long secs = (System.currentTimeMillis() - totalTime);
            // actualizer.setTotalTime(Math.round(secs * 0.001));
            // if (System.currentTimeMillis() % 10 == 0) {
            // double speed = totalRound / secs;
            // actualizer.setSpeed((double) Math.round(speed * 100000d) / 100000d);
            // }
            actualizer.setTotalPackageSize(totalPackages * 0.001);
            // String sentence = new String(receivePacket.getData(), StandardCharsets.UTF_8);
            // System.out.println("RECEIVED: " + /*new String(receivePacket.getData(), StandardCharsets.UTF_8) +*/ +
            // receivePacket.getLength());
            // InetAddress IPAddress = receivePacket.getAddress();
            // int port = receivePacket.getPort();
            // String capitalizedSentence = sentence.toUpperCase();
            // serverSocket.se
            //
            // try {
            //// serverSocket.send(sendPacket);
            // } catch (IOException e) {
            // e.printStackTrace();
            // }
            // actualizer.resetView();
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
