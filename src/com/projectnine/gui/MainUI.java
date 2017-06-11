package com.projectnine.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.util.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.projectnine.logic.IServerService;
import com.projectnine.logic.ITransferActualizer;
import com.projectnine.logic.MyFormatter;
import com.projectnine.logic.ServerService;

public class MainUI extends JFrame {

	private final JPanel setPort;
	private final Dimension MIN_WIN_SIZE = new Dimension(640, 420);
	private TCPTransferView tcpTransferView = new TCPTransferView();
	private UDPTransferView udpTransferView = new UDPTransferView();
	private final JPanel stats = new JPanel();
	private JScrollPane loggerView;
	private final static Logger logger =  Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public MainUI(IServerService service) throws HeadlessException {
		super();
		setPort = new ServerSetUpPane(service);
		this.initLogger();
		initUI();

	}

	private void initUI() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.add(setPort, BorderLayout.NORTH);
		stats.setLayout(new GridLayout(1, 2));
		stats.add(tcpTransferView);
		stats.add(udpTransferView);
		this.add(loggerView, BorderLayout.SOUTH);
		this.add(stats, BorderLayout.CENTER);
		this.setMinimumSize(MIN_WIN_SIZE);
		this.setPreferredSize(MIN_WIN_SIZE);
		this.pack();

		this.setVisible(true);
	}

	public ITransferActualizer getTCPTransferActualizer() {
		return tcpTransferView;
	}

	public ITransferActualizer getUDPTransferActualizer() {
		return udpTransferView;
	}

	private void initLogger() {
		for (Handler handler : logger.getHandlers()) {
			if (handler instanceof LoggerView) {
				LoggerView textAreaHandler = (LoggerView) handler;
				loggerView = new JScrollPane(textAreaHandler.getTextArea());
			}
		}
		if (loggerView == null) {

		}
	}

}
