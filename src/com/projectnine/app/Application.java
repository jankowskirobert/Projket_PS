package com.projectnine.app;

import com.projectnine.gui.MainUI;
import com.projectnine.logic.ConnectionService;

public class Application {
	public static void main(String[] args) {
		new MainUI(new ConnectionService());
	}
}
