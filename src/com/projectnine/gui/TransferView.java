package com.projectnine.gui;

import java.awt.Dimension;
import java.awt.font.TextLayout.CaretPolicy;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.Caret;

public class TransferView extends JPanel {

	private static final Dimension LABEL_MIN_SIZE = new Dimension(100, 30);
	private final JLabel singleDataSizeLabel = new JLabel("Singel data size:");
	private final JTextField singleDataSizeField = new JTextField ("0");
	
	{
		initUI();
	}
	
	private void initUI() {
		this.add(singleDataSizeLabel);
		this.add(singleDataSizeField);
		singleDataSizeField.setMinimumSize(LABEL_MIN_SIZE);
		singleDataSizeField.setPreferredSize(LABEL_MIN_SIZE);
		singleDataSizeField.setHorizontalAlignment(SwingConstants.RIGHT);
	}
	
}
