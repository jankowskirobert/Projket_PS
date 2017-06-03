package com.projectnine.logic;

public class ServerService implements IServerService {

	private ServerServiceStatus status = ServerServiceStatus.NONE;
	
	@Override
	public ServerServiceStatus startServer(int arg) {
		return null;
	}

	@Override
	public ServerServiceStatus getStatus() {
		return status;
	}

}
