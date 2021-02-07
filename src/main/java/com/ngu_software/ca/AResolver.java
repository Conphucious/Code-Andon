package com.ngu_software.ca;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class AResolver implements SerialPortEventListener {

	SerialPort port = new SerialPort("/dev/tty.usbmodem14201");

	public AResolver() {
		try {
			port.openPort();
			port.setParams(9600, 8, 1, 0);
			int mask = SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR;
			port.setEventsMask(mask);
			port.addEventListener(this);
		} catch (SerialPortException e) {
			e.printStackTrace();
		}
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
	
}
