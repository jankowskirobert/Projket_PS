package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import logic.IConnections;
import logic.MulticastLookup;

public class UserUI extends JFrame {

	private final DiscoveredConnections multicast;
	private final Dimension WINDOW_SIZE = new Dimension(600, 400);
	TitledBorder blackline = BorderFactory.createTitledBorder("Avaliable IP-s");
	
	
	public UserUI(IConnections connections) throws HeadlessException {
		super();
		multicast = new DiscoveredConnections(connections);
		initUI();
	}
	
	private void initUI() {
	    
		this.setLayout(new BorderLayout());
		multicast.setBorder(blackline);
		this.add(multicast, BorderLayout.WEST);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setMinimumSize(WINDOW_SIZE);
		this.setPreferredSize(WINDOW_SIZE);
		this.setVisible(true);
		this.pack();
	}
	public static void main(String[] args) {
	    MulticastLookup lk = new MulticastLookup();
		new UserUI(lk);
	}
}
