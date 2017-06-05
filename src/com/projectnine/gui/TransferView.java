package com.projectnine.gui;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import com.projectnine.logic.ITCPTransferActualizer;

public class TransferView extends JPanel implements ITCPTransferActualizer {

	private static final Dimension LABEL_MIN_SIZE = new Dimension(100, 30);
	
	private final JLabel singleDataSizeLabel = new JLabel("Singel data size[bytes]:");
	private final JTextField singleDataSizeField = new JTextField("0");
	
	private final JLabel totalDataSizeLabel = new JLabel("Total transfered data size[KB]:");
	private final JTextField totalDataSizeField = new JTextField("0");
	
	private final JLabel totalTimeLabel = new JLabel("Total transmission time[s]:");
	private final JTextField totalTimeField = new JTextField("0");
	
	private final JLabel speedLabel = new JLabel("Transmission speed[KB/s]:");
	private final JTextField speedField	 = new JTextField("0");
	
	
	private final JPanel labelsPane = new JPanel();
	private final JPanel outputPane = new JPanel();

	{
		initUI();
		SpringUtilities.makeCompactGrid(labelsPane, 4, 2, 6, 6, 6, 6);
	}

	private void initUI() {
		labelsPane.setLayout(new SpringLayout());
		labelsPane.add(singleDataSizeLabel);
		labelsPane.add(singleDataSizeField);
		labelsPane.add(totalDataSizeLabel);
		labelsPane.add(totalDataSizeField);
		
		labelsPane.add(totalTimeLabel);
		labelsPane.add(totalTimeField);
		labelsPane.add(speedLabel);
		labelsPane.add(speedField);
		singleDataSizeField.setHighlighter(null);
		singleDataSizeField.setMinimumSize(LABEL_MIN_SIZE);
		singleDataSizeField.setPreferredSize(LABEL_MIN_SIZE);
		singleDataSizeField.setHorizontalAlignment(SwingConstants.RIGHT);
		totalTimeField.setHighlighter(null);
		totalTimeField.setMinimumSize(LABEL_MIN_SIZE);
		totalTimeField.setPreferredSize(LABEL_MIN_SIZE);
		totalTimeField.setHorizontalAlignment(SwingConstants.RIGHT);
		speedField.setHighlighter(null);
		speedField.setMinimumSize(LABEL_MIN_SIZE);
		speedField.setPreferredSize(LABEL_MIN_SIZE);
		speedField.setHorizontalAlignment(SwingConstants.RIGHT);
		
		this.add(labelsPane);
		totalDataSizeField.setHighlighter(null);
		totalDataSizeField.setMinimumSize(LABEL_MIN_SIZE);
		totalDataSizeField.setPreferredSize(LABEL_MIN_SIZE);
		totalDataSizeField.setHorizontalAlignment(SwingConstants.RIGHT);
	}

	@Override
	public void setSinglePackageSize(int size) {
		this.singleDataSizeField.setText(String.valueOf(size));
		this.revalidate();
	}

	@Override
	public void setTotalPackageSize(double size) {
		this.totalDataSizeField.setText(String.valueOf(size));
		this.revalidate();
	}

	@Override
	public void setTotalTime(double seconds) {
		this.totalTimeField.setText(String.valueOf(seconds));
		this.revalidate();
		
	}

	@Override
	public void setSpeed(double speed) {
		this.speedField.setText(String.valueOf(speed));
		this.revalidate();
	}

}
