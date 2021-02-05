package com.ngu_software.ca.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;

import com.ngu_software.ca.model.FileMonitor;
import com.ngu_software.ca.ui.DialogBox;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

public class ArduinoResolver implements SerialPortEventListener {

	private SerialPort serialPort;
	private BufferedReader input;
	private OutputStream output;
	private static final int TIME_OUT = 2000;
	private static final int BALD_DATA_RATE = 9600;
	private final String PORT_KEY = "gnu.io.rxtx.SerialPorts";
	private CommPortIdentifier currPortId;
	
	private FileMonitor fileMonitor;
	
	public ArduinoResolver(String port) {
		System.setProperty(PORT_KEY, port);
		CommPortIdentifier portId = null;
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

		while (portEnum.hasMoreElements()) {
			currPortId = (CommPortIdentifier) portEnum.nextElement();
			if (currPortId.getName().equals(port)) {
				portId = currPortId;
				break;
			}
		}
		
		if (portId == null) {
			DialogBox.COM_ERROR();
			return;
		}
		
		try {
			serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);
			serialPort.setSerialPortParams(BALD_DATA_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

			input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));

			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
			
//			fileMonitor = new FileMonitor();
		} catch (Exception e) {
			DialogBox.displaySystemMessage(e);
		}
	}
	
	public synchronized void serialEvent(SerialPortEvent oEvent) {
		int test = 0;
		try {
			output = serialPort.getOutputStream();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//		FileMonitor fm = new FileMonitor(new File("pom.xml"));
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				String inputLine = input.readLine();
				System.out.println("_" + inputLine + "_");
				
				if (test == 0) {
					test = 1;
					output.write("r".getBytes());
				} else {
					output.write("y".getBytes());
					test = 0;
				}
				
				output.flush();
//				System.out.println(fm.wasModified());
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

}
