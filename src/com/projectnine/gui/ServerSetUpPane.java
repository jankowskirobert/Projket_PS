package com.projectnine.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.projectnine.logic.IServerService;
import com.projectnine.logic.ServerService;
import com.projectnine.logic.ServerServiceStatus;

public class ServerSetUpPane extends JPanel implements ActionListener {

	private final JButton runService = new JButton("Start server");
	private final JTextField portField = new JTextField();
	private final JLabel portLabel = new JLabel("Port:");
	private final JPanel portPanel = new JPanel();
	private final IServerService serverService;
	private final Dimension PORT_FILED_SIZE = new Dimension(100, 30);
	private final Dimension MIN_PANE_SIZE = new Dimension(300, 50);

	public ServerSetUpPane(IServerService serverService) {
		initUI();
		this.serverService = serverService;
	}

	private void initUI() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setMinimumSize(MIN_PANE_SIZE);
		this.setSize(MIN_PANE_SIZE);
		this.add(portLabel, BorderLayout.CENTER);
		this.add(portField, BorderLayout.CENTER);
		portField.setPreferredSize(PORT_FILED_SIZE);
		portField.setMaximumSize(PORT_FILED_SIZE);
		this.add(runService, FlowLayout.RIGHT);
		this.runService.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(runService)) {
			try {
				ServerServiceStatus status = serverService.startServer(Integer.parseInt(portField.getText()));
				if (status == null) {
					throw new NullPointerException();
				} else {
					/*
					 * TODO: STATUS HANDLE!
					 */

				}
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this.getParent(), "Could not parse server port! \n" + portField.getText(),
						"Error", JOptionPane.ERROR_MESSAGE);
			} catch (NullPointerException e2) {
				JOptionPane.showMessageDialog(this.getParent(), "Internal application error!", "Error",
						JOptionPane.ERROR_MESSAGE);
			}

		}

	}
}
