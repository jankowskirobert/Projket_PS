package com.projectnine.logic;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class TCPDriver implements Runnable, TransferActualizationSubject {

	protected int serverPort = 6660;
	protected ServerSocket serverSocket = null;
	protected boolean isStopped = false;
	protected Thread runningThread = null;
	protected Object locker = new Object();
	private final AtomicInteger c = new AtomicInteger(0);
	ExecutorService executor = Executors.newFixedThreadPool(1);
	final ReentrantLock rl = new ReentrantLock();

	private ITCPTransferActualizer actualizer;

	public TCPDriver(int port) {
		this.serverPort = port;
	}

	public void run() {
		
		openServerSocket();
		while (!isStopped()) {
			
			Socket clientSocket = null;
			try {
				if (rl.isHeldByCurrentThread())
					System.out.printf("Thread %s has entered its critical section.%n", "");
				System.out.printf("Thread %s is performing work for 2 seconds.%n", "");

				rl.lock();
				clientSocket = this.serverSocket.accept();
			} catch (IOException e) {
				if (isStopped()) {
					System.out.println("Server Stopped.");
					return;
				}
				throw new RuntimeException("Error accepting client connection", e);
			} finally {
				rl.unlock();
			}
			Runnable r = new WorkerRunnable(clientSocket, "Multithreaded Server", actualizer);
			executor.submit(r);
		}
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
			throw new RuntimeException("Error closing server", e);
		}
	}

	private void openServerSocket() {
		try {
			this.serverSocket = new ServerSocket(this.serverPort);
		} catch (IOException e) {
			throw new RuntimeException("Cannot open port 8080", e);
		}
	}

	@Override
	public void setActualizer(ITCPTransferActualizer actualizer) {
		this.actualizer = actualizer;

	}
}
