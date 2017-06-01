package com.projectnine.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainUI extends JFrame  {

	private JPanel setPort = new SetPortPane();
	private final Dimension MIN_WIN_SIZE = new Dimension(600, 400);
	public MainUI() throws HeadlessException {
		super();
		initUI();
	}

	private void initUI(){
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.add(setPort);
		this.setMinimumSize(MIN_WIN_SIZE);
		this.setPreferredSize(MIN_WIN_SIZE);
		this.pack();
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		new MainUI();
	}
}
