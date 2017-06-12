package com.projectnine.app;

import java.util.logging.Logger;

import com.projectnine.gui.LoggerView;
import com.projectnine.gui.MainUI;
import com.projectnine.logic.ConnectionService;

public class Application {
    private final static Logger logger =  Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	public static void main(String[] args) {
	    
	    logger.addHandler(new LoggerView());
	    ConnectionService cs = new ConnectionService();
		MainUI gui = new MainUI(cs);
		
	}
}
