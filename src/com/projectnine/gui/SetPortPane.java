package com.projectnine.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SetPortPane extends JPanel implements ActionListener {
	
	private final JTextField listenOnPort = new JTextField();
	private final JButton configurePort = new JButton();
	
	public SetPortPane() {
		initUI();
	}
	
	private void initUI(){
		this.setLayout(new BorderLayout());
		this.add(listenOnPort);
		this.add(configurePort);
		this.configurePort.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
