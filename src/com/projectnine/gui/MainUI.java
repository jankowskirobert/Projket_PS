package com.projectnine.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.projectnine.logic.IServerAction;

public class MainUI extends JFrame implements ActionListener {

	private final JLabel udpStatus = new JLabel("UDP Active", SwingConstants.CENTER);
	private final JLabel tcpStatus = new JLabel("TCP Active", SwingConstants.CENTER);
	private final JPanel serverStatus = new JPanel();
	private final JPanel loggerView = new JPanel();
	private final JPanel maniupulatorView = new JPanel();
	private final JPanel configView = new JPanel();
	private final Dimension MIN_WINDOW = new Dimension(640, 220);

	// private final JButton connectToServer = new JButton("Connect");
	private final JTextField serverPort = new JTextField(5);
	private final JLabel serverPortLabel = new JLabel("Port:", SwingConstants.RIGHT);
	private final JTextField serverAddress = new JTextField(15);
	private final JLabel serverAddressLabel = new JLabel("Address:", SwingConstants.RIGHT);

	private final JSlider framesPerSecond = new JSlider(JSlider.HORIZONTAL, 1, 65000, 1);
	private final JButton transferButton = new JButton("Start");
	private final JCheckBox nagleBox = new JCheckBox("nagle");
	private final JTextField packageSize = new JTextField(5);
	private final IServerAction serverAction;

	private boolean serverRunning = false;

	public MainUI(IServerAction serverAction) throws HeadlessException {
		super();
		this.serverAction = serverAction;
		configureUI();
		initStatusPane();
		initConfigPane();
		initManipulationPane();
		this.pack();
		this.setVisible(true);
	}

	private void configureUI() {
		this.setMinimumSize(MIN_WINDOW);
		this.setPreferredSize(MIN_WINDOW);
		this.setLayout(new BorderLayout());
		this.add(serverStatus, BorderLayout.SOUTH);
		this.add(maniupulatorView, BorderLayout.CENTER);
		this.add(configView, BorderLayout.NORTH);
		this.add(loggerView, BorderLayout.EAST);

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	private void initStatusPane() {
		serverStatus.setLayout(new GridLayout(1, 2));
		serverStatus.add(udpStatus);
		serverStatus.add(tcpStatus);
	}

	private void initConfigPane() {
		configView.setLayout(new GridLayout(1, 5));
		// configView.add(connectToServer);
		configView.add(serverPortLabel);
		configView.add(serverPort);
		configView.add(serverAddressLabel);
		configView.add(serverAddress);
	}

	private void initManipulationPane() {
		framesPerSecond.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				packageSize.setText(String.valueOf(framesPerSecond.getValue()));
			}
		});
		Hashtable labelTable = new Hashtable();
		labelTable.put(new Integer(1), new JLabel("Small package"));
		labelTable.put(new Integer(65000), new JLabel("Big package"));
		framesPerSecond.setLabelTable(labelTable);
		framesPerSecond.setPaintTicks(true);
		framesPerSecond.setPaintLabels(true);
		maniupulatorView.setLayout(new BorderLayout());
		maniupulatorView.add(framesPerSecond, BorderLayout.CENTER);
		maniupulatorView.add(transferButton, BorderLayout.SOUTH);
		maniupulatorView.add(nagleBox, BorderLayout.EAST);
		maniupulatorView.add(packageSize, BorderLayout.WEST);
		transferButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(transferButton)) {
			if (!serverRunning)
				try {
					int port = Integer.parseInt(serverPort.getText());
					InetAddress addr = InetAddress.getByName(serverAddress.getText());
					int dataSize = Integer.parseInt(packageSize.getText());
					serverAction.startService(addr, port, dataSize);
					serverRunning = true;
					transferButton.setText("Stop");
				} catch (NumberFormatException | IOException err) {
					JOptionPane.showMessageDialog(this, err.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					serverRunning = false;
				}
			else {
				serverAction.shouldStop();
				serverRunning = false;
				transferButton.setText("Start");
			}
		}
	}
}
