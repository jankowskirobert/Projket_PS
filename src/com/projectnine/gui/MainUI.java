package com.projectnine.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.projectnine.logic.IServerService;
import com.projectnine.logic.ITransferActualizer;
import com.projectnine.logic.ServerService;

public class MainUI extends JFrame {

    private final JPanel setPort;
    private final Dimension MIN_WIN_SIZE = new Dimension(640, 420);
    private TCPTransferView tcpTransferView = new TCPTransferView();
    private UDPTransferView udpTransferView = new UDPTransferView();
    private final JPanel stats = new JPanel(); 
    public MainUI(IServerService service) throws HeadlessException {
        super();
        setPort = new ServerSetUpPane(service);
        initUI();
    }

    private void initUI() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.add(setPort, BorderLayout.NORTH);
        stats.setLayout(new GridLayout(1, 2));
        stats.add(tcpTransferView);
        stats.add(udpTransferView);
        this.add(stats, BorderLayout.CENTER);
        this.setMinimumSize(MIN_WIN_SIZE);
        this.setPreferredSize(MIN_WIN_SIZE);
        this.pack();
        this.setVisible(true);
    }

    public ITransferActualizer getTCPTransferActualizer() {
        return tcpTransferView;
    }

    public ITransferActualizer getUDPTransferActualizer() {
        return udpTransferView;
    }
    // public static void main(String[] args) {
    // new MainUI();
    // }
}
