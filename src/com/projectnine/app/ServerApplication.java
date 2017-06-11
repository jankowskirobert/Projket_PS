package com.projectnine.app;

import java.util.logging.Logger;

import com.projectnine.gui.LoggerView;
import com.projectnine.gui.MainUI;
import com.projectnine.logic.MyFormatter;
import com.projectnine.logic.ServerService;
import com.projectnine.logic.ServerServiceStatus;
import com.projectnine.logic.TCPDriver;
import com.projectnine.logic.UDPDriver;

public class ServerApplication {
	private final static Logger logger =  Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public static void main(String[] args) {
		LoggerView jta = new LoggerView();
		jta.setFormatter(new MyFormatter());
		logger.addHandler(jta);
        TCPDriver driverTCP = new TCPDriver();
        UDPDriver driverUDP = new UDPDriver();

        ServerService serverService = new ServerService(driverTCP, driverUDP);
        MainUI gui = new MainUI(serverService);
        driverTCP.setActualizer(gui.getTCPTransferActualizer());
        driverUDP.setActualizer(gui.getUDPTransferActualizer());
//        serverService.setTCPActualizer(gui.getTransferActualizer());
    }
}
