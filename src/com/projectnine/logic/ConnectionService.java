package com.projectnine.logic;

import java.net.ConnectException;
import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConnectionService implements IServerAction {

    private TCPClient tcp = new TCPClient();
    private UDPClient udp = new UDPClient();;
    // ExecutorService executorServiceTcp = Executors.newFixedThreadPool(2);

    @Override
    public void startService(InetAddress address, int port, int dataSize) {

        tcp.setAddress(address);
        tcp.setPort(port);
        tcp.setByteSize(dataSize);
        udp.setIpAddress(address);
        udp.setPort(port);
        udp.setByteSize(dataSize);

        try {

            new Thread(tcp).start();

            new Thread(udp).start();
        } catch (IllegalThreadStateException e) {

        }

    }

    public TCPClient getTcp() {
        return tcp;
    }

    public void setTcp(TCPClient tcp) {
        this.tcp = tcp;
    }

    public UDPClient getUdp() {
        return udp;
    }

    public void setUdp(UDPClient udp) {
        this.udp = udp;
    }

    public ConnectionService() {
        super();
    }

    @Override
    public boolean shouldStop() {
        tcp.shouldStop();
        udp.shouldStop();
        return false;
    }

    @Override
    public void nagleAlorithm(boolean flag) {
        tcp.setNagleFlag(flag);
    }

}
