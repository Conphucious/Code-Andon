package com.ngu_software.ca.controller;

import java.io.File;

import com.ngu_software.ca.model.LogMonitor;
import com.ngu_software.ca.view.CAMenu;
import com.ngu_software.ca.view.DialogBox;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class ArduinoSerialResolver implements SerialPortEventListener {

	private SerialPort port;
	private LogMonitor lmBuildCompile, lmRuntime;

	public ArduinoSerialResolver(String comPort, String buildLogFile, String runtimeLogFile) {
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

		CAMenu.toggleActionMenuItem();
	}

	public void serialEvent(SerialPortEvent event) {
		if (event.isRXCHAR() && event.getEventValue() > 0) {
			try {
				// need token here to resolve start and end of serial command.
				String receivedData = port.readString(1).trim().replace("\n", "");//.readString(event.getEventValue());
				if (!receivedData.isEmpty()) {
					System.out.println("Received response: " + receivedData);
				}
			} catch (SerialPortException e) {
				e.printStackTrace();
			}
		}
	}

	public void close() {
		try {
			port.closePort();
			CAMenu.toggleActionMenuItem();
		} catch (SerialPortException e) {
			e.printStackTrace();
		}
	}
}
