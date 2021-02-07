package com.ngu_software.ca.controller;

import com.ngu_software.ca.ui.CAMenu;
import com.ngu_software.ca.ui.DialogBox;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class ArduinoSerialResolver implements SerialPortEventListener {

	private SerialPort port;

	public ArduinoSerialResolver(String comPortName) {
		try {
			port = new SerialPort(comPortName);
			port.openPort();
			port.setParams(9600, 8, 1, 0);
			int mask = SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR;
			port.setEventsMask(mask);
			port.addEventListener(this);
		} catch (SerialPortException e) {
			DialogBox.displaySystemMessage(e);
			return;
		}

		CAMenu.getActionMenu().setEnabled(true);
		CAMenu.getActionMenu().setLabel("Stop");
	}

	public void serialEvent(SerialPortEvent event) {
		if (event.isRXCHAR() && event.getEventValue() > 0) {
			try {
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
			CAMenu.getActionMenu().setLabel("Start");
		} catch (SerialPortException e) {
			e.printStackTrace();
		}
	}
	
}
