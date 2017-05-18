package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;

public class DiscoveredConnections extends JPanel {

	
	private final DefaultListModel<String> listModel = new DefaultListModel<>(); 
	private final JList<String> discoveredAddresses = new JList<String>(listModel);
	private final Dimension WINDOW_SIZE = new Dimension(150, 400);
	public DiscoveredConnections() {
		super();
		initList();
		initUI();
	}

	private void initList() {
		listModel.addElement("Test");
		
	}
	
	private void initUI() {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.add(discoveredAddresses);
		
//		this.setBackground(Color.BLUE);
	}
	
}
