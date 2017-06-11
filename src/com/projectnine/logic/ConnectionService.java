package com.projectnine.logic;

import java.net.ConnectException;
import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class 
ConnectionService implements IServerAction {

	private TCPClient tcp ;
	private UDPClient udp ;
	ExecutorService executorServiceTcp = Executors.newFixedThreadPool(2);
	
	@Override
	public void startService(InetAddress address, int port, int dataSize){
		if(tcp == null)
			tcp = new TCPClient(address, port, dataSize);
		tcp.setByteSize(dataSize);
		executorServiceTcp.execute(tcp);
		if(udp == null)
			udp= new UDPClient(address, port, dataSize);
		udp.setByteSize(dataSize);
		executorServiceTcp.execute(udp);
	}

	@Override
	public boolean shouldStop() {
//		tcp.interrupt();//stop();
//		tcp.stop();
tcp.shouldStop();
udp.shouldStop();
return false;
	}

}
