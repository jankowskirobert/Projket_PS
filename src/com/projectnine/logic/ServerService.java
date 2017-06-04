package com.projectnine.logic;

public class ServerService implements IServerService {

	private ServerServiceStatus status = ServerServiceStatus.NONE;
	private ITCPTransferActualizer actualizer;
	
	@Override
	public ServerServiceStatus startServer(int arg) {
		TCPDriver driver = new TCPDriver(6660);
		if(actualizer != null){
			driver.setActualizer(actualizer);
		}
		
		try {
			new Thread(driver).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.status = ServerServiceStatus.ONLY_TCP;
		return status;
	}

	@Override
	public ServerServiceStatus getStatus() {
		return status;
	}

	@Override
	public void setActualizer(ITCPTransferActualizer actualizer) {
		this.actualizer = actualizer;
	}

}
