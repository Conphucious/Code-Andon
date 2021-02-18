package com.ngu_software.ca.controller;

import java.io.File;

import com.ngu_software.ca.model.LogMonitor;
import com.ngu_software.ca.model.SerialCommand;
import com.ngu_software.ca.view.CAMenu;
import com.ngu_software.ca.view.DialogBox;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class ArduinoSerialResolverIDE implements SerialPortEventListener {

	private SerialPort port;
	private LogMonitor lmBuildCompile, lmRuntime;

	private StringBuilder message = new StringBuilder();
	private Boolean startingToken = false;

	private Boolean isStartup = true;
	private Boolean isSetDisabled = false;
	
	public ArduinoSerialResolverIDE(String comPort, String buildLogFile, String runtimeLogFile) {
		try {
			port = new SerialPort(comPort);
			port.openPort();
			port.setParams(9600, 8, 1, 0);
			port.setEventsMask(SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR);
			port.addEventListener(this);

			lmBuildCompile = new LogMonitor(new File(buildLogFile));
			lmRuntime = new LogMonitor(new File(runtimeLogFile));
		} catch (SerialPortException e) {
			DialogBox.showSystemMessage(e);
			return;
		}
		CAMenu.toggleIdeActionMenuItem();
	}

	// Optimize all the if statements.
	public void serialEvent(SerialPortEvent event) {
		if (event.isRXCHAR() && event.getEventValue() > 0) {
			try {
				byte buffer[] = port.readBytes();
				for (byte b : buffer) {
					if (b == '#') {
						startingToken = true;
						message.setLength(0);
					} else if (startingToken == true) {
						if (b == '!') {
							if (lmBuildCompile.wasModified() || lmRuntime.wasModified()) { // just to keep it initially off. Maybe move scope?
								isStartup = false;
							}
							startingToken = false;
							if (lmBuildCompile != null && lmRuntime != null) {		// reduce the if statements. Maybe function?
								if (!isSetDisabled && !isStartup) {
									if (lmBuildCompile.isSuccessful() && lmRuntime.isSuccessful()) {
										port.writeString(SerialCommand.TX_GREEN_LIGHT.getValue());
									} else if (lmBuildCompile.isError() || lmRuntime.isError()) {
										port.writeString(SerialCommand.TX_RED_LIGHT.getValue());
									} else if (lmBuildCompile.isWarning() || lmRuntime.isWarning()) {
										port.writeString(SerialCommand.TX_YELLOW_LIGHT.getValue());
									}
								}
								if (message.toString().trim().equals(SerialCommand.TX_RESET_PRESSED.getValue())) {
									isSetDisabled = true;
									lmRuntime.clearFileContents();
								} else if (lmBuildCompile.wasModified() || (lmRuntime.wasModified() && !lmRuntime.isSuccessful())) {
									isSetDisabled = false;
								}
							}
						} else {
							message.append((char) b);
						}
					}
				}
			} catch (SerialPortException e) {
				DialogBox.showSystemMessage(e);
			}
		}
	}
	
	public void resetFiles() {
		lmBuildCompile.clearFileContents();
		lmRuntime.clearFileContents();
	}

	public void close() {
		try {
			if (port.isOpened()) {
				port.closePort();
			}
			CAMenu.toggleIdeActionMenuItem();
		} catch (SerialPortException e) {
			DialogBox.showSystemMessage(e);
		}
	}
}
