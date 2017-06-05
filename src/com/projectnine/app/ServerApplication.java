package com.projectnine.app;

import com.projectnine.gui.MainUI;
import com.projectnine.logic.ServerService;
import com.projectnine.logic.ServerServiceStatus;
import com.projectnine.logic.TCPDriver;
import com.projectnine.logic.UDPDriver;

public class ServerApplication {

    public static void main(String[] args) {
        TCPDriver driverTCP = new TCPDriver();
        UDPDriver driverUDP = new UDPDriver();

        ServerService serverService = new ServerService(driverTCP, driverUDP);
        MainUI gui = new MainUI(serverService);
        driverTCP.setActualizer(gui.getTCPTransferActualizer());
        driverUDP.setActualizer(gui.getUDPTransferActualizer());
//        serverService.setTCPActualizer(gui.getTransferActualizer());
    }
}
