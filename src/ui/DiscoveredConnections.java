package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;

import logic.IConnections;

public class DiscoveredConnections extends JPanel implements ActionListener{

	
	private final DefaultListModel<String> listModel = new DefaultListModel<>(); 
	private final JList<String> discoveredAddresses = new JList<String>(listModel);
	private final Dimension WINDOW_SIZE = new Dimension(200, 400);
	private final Dimension BUTTONSPANE_SIZE = new Dimension(200, 50);
	private final JButton buttonSearchConnections = new JButton("Search IP-s");
	private final JButton buttonConnectToServer = new JButton("Connect");
	private final IConnections connections;
	private final JPanel buttonsPane = new JPanel();
	public DiscoveredConnections(IConnections connections) {
		super();
		this.connections = connections;
		initList();
		initUI();
	}

	private void initList() {
		listModel.addElement("Empty");	
	}
	
	private void initButtonsPane(){
	    this.buttonsPane.setMinimumSize(BUTTONSPANE_SIZE);
	    this.buttonsPane.setPreferredSize(BUTTONSPANE_SIZE);
	    this.buttonsPane.setLayout(new BoxLayout(buttonsPane, BoxLayout.LINE_AXIS));
	    this.buttonsPane.add(buttonSearchConnections);
	    this.buttonsPane.add(buttonConnectToServer);
	    this.buttonSearchConnections.addActionListener(this);
	    this.buttonConnectToServer.addActionListener(this);
	}
	
	private void initUI() {
		this.setLayout(new BorderLayout());
		this.add(discoveredAddresses, BorderLayout.CENTER);
		this.setMinimumSize(WINDOW_SIZE);
        this.setPreferredSize(WINDOW_SIZE);
        this.add(buttonsPane, BorderLayout.SOUTH);
	}

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(buttonSearchConnections)){
            listModel.clear();
            for(String result: connections.connection()){
                listModel.addElement(result);
            }
        }
        
    }
	
}
