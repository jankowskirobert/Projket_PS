package com.projectnine.logic;

public class ServerService implements IServerService {

	private ServerServiceStatus status = ServerServiceStatus.NONE;
	private ITransferActualizer actualizerTCP;
	private final TCPDriver driverTCP;
	private final UDPDriver driverUDP;
	
	
	public ServerService(TCPDriver driverTCP, UDPDriver driverUDP) {
        super();
        this.driverTCP = driverTCP;
        this.driverUDP = driverUDP;
    }

    @Override
	public ServerServiceStatus startServer(int arg) {
        this.driverTCP.setServerPort(arg);
        this.driverUDP.setServerPort(arg);
		try {
			new Thread(driverTCP).start();
			new Thread(driverUDP).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.status = ServerServiceStatus.BOTH_TCP_UDP;
		return status;
	}

	@Override
	public ServerServiceStatus getStatus() {
		return status;
	}


}
