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
import java.util.logging.Logger;

public class TCPClient extends Observable implements Runnable, IServerStopper {

    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private InetAddress address;
    private int port;
    private int byteSize;
    private boolean flag;

    public TCPClient() {
        super();
    }

    public InetAddress getAddress() {
        return address;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    private boolean nagleFlag = false;

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
        for (int i = 0; i < byteSize; i++) {
            arr[i] = b;
        }
        return new String(arr);
    }

    @Override
    public void run() {
        flag = false;
        String sentence = convertByteToString() + "\n";
        Socket clientSocket = null;
        try {
            logger.info("TCP Client Started");
            clientSocket = new Socket(address, port);
            clientSocket.setTcpNoDelay(nagleFlag);
            clientSocket.setSendBufferSize(byteSize);
            logger.info("TCP Client Started");
            OutputStream strOut = clientSocket.getOutputStream();
            strOut.write(String.valueOf("SIZE:" + byteSize).getBytes());
            strOut.flush();
            while (!flag) {
                Thread.sleep(2);
                strOut.write(sentence.getBytes());
                strOut.flush();
            }
            strOut.close();
            clientSocket.close();

        } catch (IOException | InterruptedException e) {
            setChanged();
            notifyObservers(e);
            e.printStackTrace();
        }
    }

    public boolean isNagleFlag() {
        return nagleFlag;
    }

    public void setNagleFlag(boolean nagleFlag) {
        this.nagleFlag = nagleFlag;
    }

    @Override
    public boolean shouldStop() {
        this.flag = true;
        return false;
    }

}
