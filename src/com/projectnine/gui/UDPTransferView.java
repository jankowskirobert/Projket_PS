package com.projectnine.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import com.projectnine.logic.ITransferActualizer;
import com.projectnine.logic.ServerServiceStatus;
import com.projectnine.logic.ServerStatus;

import observer.Listener;

public class UDPTransferView extends JPanel implements ITransferActualizer {

    private static final Dimension LABEL_MIN_SIZE = new Dimension(130, 20);

    private final JLabel singleDataSizeLabel = new JLabel("Singel data size[bytes]:");
    private final JTextField singleDataSizeField = new JTextField("0");

    private final JLabel totalDataSizeLabel = new JLabel("Total transfered data size[KB]:");
    private final JTextField totalDataSizeField = new JTextField("0");

    private final JLabel totalTimeLabel = new JLabel("Total transmission time[s]:");
    private final JTextField totalTimeField = new JTextField("0");

    private final JLabel speedLabel = new JLabel("Transmission speed[MBytes/s]:");
    private final JTextField speedField = new JTextField("0");
    TitledBorder titled = new TitledBorder("UDP");
    private final JLabel serverStatusLabel = new JLabel("Status: ");
    private final JLabel serverStatus = new JLabel("");
    private final JPanel labelsPane = new JPanel();
    private final JPanel outputPane = new JPanel();

    {
        initUI();
        SpringUtilities.makeCompactGrid(labelsPane, 4, 2, 6, 6, 6, 6);
    }

    private void initUI() {
        this.setLayout(new BorderLayout());
        this.setBorder(titled);
        JPanel tmp = new JPanel();
        tmp.setLayout(new BoxLayout(tmp, BoxLayout.X_AXIS));
        tmp.add(serverStatusLabel);
        tmp.add(serverStatus);
        this.add(tmp, BorderLayout.SOUTH);

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
        singleDataSizeField.setMaximumSize(LABEL_MIN_SIZE);
        singleDataSizeField.setPreferredSize(LABEL_MIN_SIZE);
        singleDataSizeField.setHorizontalAlignment(SwingConstants.RIGHT);
        totalTimeField.setHighlighter(null);
        totalTimeField.setMaximumSize(LABEL_MIN_SIZE);
        totalTimeField.setPreferredSize(LABEL_MIN_SIZE);
        totalTimeField.setHorizontalAlignment(SwingConstants.RIGHT);
        speedField.setHighlighter(null);
        speedField.setMaximumSize(LABEL_MIN_SIZE);
        speedField.setPreferredSize(LABEL_MIN_SIZE);
        speedField.setHorizontalAlignment(SwingConstants.RIGHT);

        this.add(labelsPane);
        totalDataSizeField.setHighlighter(null);
        totalDataSizeField.setMaximumSize(LABEL_MIN_SIZE);
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

    @Override
    public void updateStatus(ServerStatus status) {
        this.serverStatus.setText(status.name());
    }

    @Override
	public void resetView() {
		 this.singleDataSizeField.setText("");
		 this.totalDataSizeField.setText("");
		 this.totalTimeField.setText("");
		 this.speedField.setText("");
	}

}
