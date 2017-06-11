package com.projectnine.gui;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class LoggerView extends Handler {
	private JTextArea textArea = new JTextArea(5, 50);

	@Override
	public void publish(LogRecord record) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				StringWriter text = new StringWriter();
				PrintWriter out = new PrintWriter(text);
				out.println(textArea.getText());
				out.println("["+record.getLevel() + "] <" + new Date(record.getMillis()) + "> "+record.getMessage() );
				textArea.setText(text.toString());
			}
		});
	}
	@Override
	public void flush() {
		// TODO Auto-generated method stub

	}

	@Override
	public void close() throws SecurityException {
		// TODO Auto-generated method stub

	}

	public JTextArea getTextArea() {
		return textArea;
	}

	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}

}
