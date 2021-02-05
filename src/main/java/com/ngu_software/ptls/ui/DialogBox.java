package com.ngu_software.ptls.ui;

import javax.swing.JOptionPane;

public class DialogBox {
	
	private static final String TITLE = "JPTLS";

	public static final void COM_ERROR() {
		JOptionPane.showMessageDialog(null, "COM Port was not found!", TITLE, 1);
	}

}
