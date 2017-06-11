package com.projectnine.logic;

import java.net.InetAddress;

public interface IServerAction extends IServerStopper{
	public void startService(InetAddress address, int port, int dataSize);
}
