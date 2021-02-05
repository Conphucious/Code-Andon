package com.ngu_software.ca.ui;

import javax.swing.JOptionPane;

public class DialogBox {
	
	private static final String TITLE = "JPTLS";

	public static final void COM_ERROR() {
		JOptionPane.showMessageDialog(null, "COM Port was not found!", TITLE, 1);
	}
	
	public static void unsetPortMessage() {
		JOptionPane.showMessageDialog(null, "COM Port has not yet been set", TITLE, 1);
	}
	
	public static void displaySystemMessage(Exception e) {
		JOptionPane.showMessageDialog(null, e.toString(), TITLE, 1);
	}
	
	public static String getInput(String title) {
		return JOptionPane.showInputDialog(title);
	}
	
	

}
