package com.projectnine.logic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.projectnine.gui.LoggerView;

public class TCPDriver extends Observable implements Runnable, TransferActualizationSubject {
	private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	protected int serverPort = 6660;
	protected ServerSocket serverSocket = null;
	protected boolean isStopped = false;
	protected Thread runningThread = null;
	protected Object locker = new Object();
	private final AtomicInteger c = new AtomicInteger(0);
	ExecutorService executor = Executors.newFixedThreadPool(1);
	final ReentrantLock rl = new ReentrantLock();
	private boolean serverWorks = false;
	
    public boolean isServerWorks() {
        return serverWorks;
    }

    
    public void setServerWorks(boolean serverWorks) {
        this.serverWorks = serverWorks;
    }

    private ITransferActualizer actualizer;
	private int totalPackages;
	private long timeStart;

	public TCPDriver(int port) {
		this.serverPort = port;

	}

	public TCPDriver() {
		this.serverPort = 7777;

	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	DecimalFormat f = new DecimalFormat("##.00");
	int oldSingle = 0;
	public void run() {
	    serverWorks = false;
		openServerSocket();
		try {
			while (!serverWorks) {
				Socket socket = serverSocket.accept();
				serverSocket.close();
				System.out.println("Connection accepted: " + socket);
				logger.info("Connection accepted: " + socket);
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),
						true);
				InputStream inputStream = socket.getInputStream();
				totalPackages = 0;
				String initialMsg = convertStreamToString(inputStream);
	            if(initialMsg.contains("SIZE")){
	            	String[] arry = initialMsg.split(":");
	            	arry[1] = arry[1].replaceAll("\\D+","");
	            	logger.info("TCP: New Client, package size:" + arry[1]);
	            	System.out.println("TCP: New Client, package size:" + arry[1]);
	            	oldSingle = Integer.parseInt(arry[1]);
	            	actualizer.setSinglePackageSize(oldSingle);
	            }
				byte[] buffer = new byte[65536];
//				int read;
				int sizePackage = 0;
//				if ((read = inputStream.read(buffer)) != -1) {
//					sizePackage = inputStream.read(buffer);
//					
//					if (c != null)
//						actualizer.setSinglePackageSize(sizePackage);
//				}
	            sizePackage = oldSingle;
				if (sizePackage > 0) {
					buffer = new byte[sizePackage];
					int data = inputStream.read(buffer);
					totalPackages = 0;
					this.timeStart = System.currentTimeMillis();
					Thread.sleep(2);
					while ((data = inputStream.read(buffer)) != -1) {

						if (data > 0) {
							totalPackages += data;

							actualizer.setTotalPackageSize(f.parse(f.format(totalPackages * 0.001)).doubleValue());
							long estimatedTime = System.currentTimeMillis() - timeStart;

							actualizer.setTotalTime(f.parse(f.format(TimeUnit.MILLISECONDS.toSeconds(estimatedTime))).longValue());

							actualizer.setSpeed(
									f.parse(f.format((totalPackages * 0.001) / (estimatedTime * 0.001))).doubleValue());

						}
					}
					totalPackages = 0;
					inputStream.close();
				}
				logger.info("Client Disconnected!: " + socket);
				openServerSocket();
			}

		} catch (IOException | ParseException | InterruptedException e) {
			logger.severe(e.getMessage());
			e.printStackTrace();
		}

		actualizer.updateStatus(ServerStatus.STOPPED);
		System.out.println("Server Stopped.");

	}

	private synchronized boolean isStopped() {
		return this.isStopped;
	}

	public synchronized void stop() {
		this.isStopped = true;
		try {
			this.serverSocket.close();
		} catch (IOException e) {
			logger.severe(e.getMessage());
			throw new RuntimeException("Error closing server", e);
		}
	}

	private void openServerSocket() {
		try {
			this.serverSocket = new ServerSocket(this.serverPort);
			logger.info("TCP Started");
			actualizer.updateStatus(ServerStatus.RUNNING);
		} catch (IOException e) {
			actualizer.updateStatus(ServerStatus.FAILED);
			setChanged();
            notifyObservers(e);
			throw new RuntimeException("Cannot open port 8080", e);
		}
	}

	@Override
	public void setActualizer(ITransferActualizer actualizer) {
		this.actualizer = actualizer;

	}
	static String convertStreamToString(java.io.InputStream is) throws IOException {
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		byte[] buffer = new byte[65000];
		int length;
		if ((length = is.read(buffer)) != -1) {
		    result.write(buffer, 0, length);
		}

		return result.toString("UTF-8");
	}
}
