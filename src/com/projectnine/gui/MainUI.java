package com.projectnine.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.projectnine.logic.IServerService;
import com.projectnine.logic.ITCPTransferActualizer;
import com.projectnine.logic.ServerService;

public class MainUI extends JFrame  {

	private final JPanel setPort;
	private final Dimension MIN_WIN_SIZE = new Dimension(600, 400);
	private TransferView transferView = new TransferView();
	
	public MainUI(IServerService service) throws HeadlessException {
		super();
		setPort = new ServerSetUpPane(service);	
		initUI();
	}

	private void initUI(){
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.add(setPort, BorderLayout.NORTH);
		this.add(transferView, BorderLayout.CENTER);
		this.setMinimumSize(MIN_WIN_SIZE);
		this.setPreferredSize(MIN_WIN_SIZE);
		this.pack();
		this.setVisible(true);
	}
	
	public ITCPTransferActualizer getTransferActualizer() {
		return transferView;
	}
	
//	public static void main(String[] args) {
//		new MainUI();
//	}
}
