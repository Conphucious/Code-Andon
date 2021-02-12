package com.ngu_software.ca.model;

public enum SerialCommand {
	TX_RED_LIGHT("RED"),
	TX_YELLOW_LIGHT("YELLOW"),
	TX_GREEN_LIGHT("GREEN"),
	TX_ALL_LIGHT_OFF("OFF"),
	TX_RESET_PRESSED("RESET");

	private final String value;
	private SerialCommand(String value) { this.value = value;}
	
	public String getValue() {
		return value;
	}
	
	public byte[] getBytes() {
		return (this.value.toString()).getBytes();
	}
	
}
