package com.projectnine.logic;

public interface IServerService extends IServerStopper {
	public ServerServiceStatus startServer(int i);
	public ServerServiceStatus getStatus(); 
}
