package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class UserUI extends JFrame {

	private final DiscoveredConnections multicast = new DiscoveredConnections();
	private final Dimension WINDOW_SIZE = new Dimension(600, 400);
	
	public UserUI() throws HeadlessException {
		super();
		initUI();
	}
	private void initUI() {
		this.setLayout(new BorderLayout());
		this.add(multicast, BorderLayout.WEST);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setMinimumSize(WINDOW_SIZE);
		this.setPreferredSize(WINDOW_SIZE);
		this.setVisible(true);
		this.pack();
	}
	public static void main(String[] args) {
		new UserUI();
	}
}
