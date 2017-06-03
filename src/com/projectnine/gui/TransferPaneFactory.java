package com.projectnine.gui;

import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TransferPaneFactory {
	
	public static JPanel getTransferPanel(List<String> labels, List<JTextField> fields){
		JPanel result = new JPanel();
		JPanel columnRight= new JPanel();
		JPanel columnLeft= new JPanel();
		for(String label:labels){
			columnLeft.add(new JLabel(label));	
		}
		fields.clear();
		for(String label:labels){
			JTextField field = new JTextField();
			columnLeft.add(label, field);
			field.add(field);
		}
		
		
		return result;
	}
	
}
