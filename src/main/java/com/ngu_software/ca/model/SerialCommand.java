package com.ngu_software.ca.model;

public enum SerialCommand {
	TX_RED_LIGHT('r'),
	TX_YELLOW_LIGHT('y'),
	TX_GREEN_LIGHT('g'),
	TX_ALL_LIGHT_OFF('x');
	// Retrieve the above from a properties file.

	private final Character charVal;
	private SerialCommand(Character charVal) { this.charVal = charVal;}
	
	public byte[] getBytes() {
		return (this.charVal.toString()).getBytes();
	}
	
}
