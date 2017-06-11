package com.projectnine.logic;

import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class 
ConnectionService implements IServerAction {

	private TCPClient tcp ;
	ExecutorService executorServiceTcp = Executors.newFixedThreadPool(1);
	
	@Override
	public void startService(InetAddress address, int port, int dataSize) {
		if(tcp == null)
			tcp = new TCPClient(address, port, dataSize);
		tcp.setByteSize(dataSize);
		executorServiceTcp.submit(tcp);

	}

	@Override
	public boolean shouldStop() {
//		tcp.interrupt();//stop();
//		tcp.stop();
tcp.shouldStop();
return false;
	}

}
