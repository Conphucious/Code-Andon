package com.ngu_software.ca.ui;

import javax.swing.JOptionPane;

public class DialogBox {
	
	private static final String TITLE = "JPTLS";
	
	public static void aboutMessage() {
		JOptionPane.showMessageDialog(null, "https://github.com/Conphucious", TITLE, 1);
	}

	public static final void comPortNotFoundMessage() {
		JOptionPane.showMessageDialog(null, "COM Port was not found!", TITLE, 1);
	}
	
	public static void comPortNotSetMessage() {
		JOptionPane.showMessageDialog(null, "COM Port has not yet been set", TITLE, 1);
	}
	
	public static String getInput(String title) {
		return JOptionPane.showInputDialog(title);
	}
	
	public static void displaySystemMessage(Exception e) {
		JOptionPane.showMessageDialog(null, e.toString(), TITLE, 1);
	}
	
	

}
