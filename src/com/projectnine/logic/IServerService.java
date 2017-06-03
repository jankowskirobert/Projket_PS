package com.projectnine.logic;

public interface IServerService extends TransferActualizationSubject {
	public ServerServiceStatus startServer(int i);
	public ServerServiceStatus getStatus(); 
}
