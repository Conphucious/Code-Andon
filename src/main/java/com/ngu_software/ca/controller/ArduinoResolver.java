package com.ngu_software.ca.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;

import com.ngu_software.ca.model.FileMonitor;
import com.ngu_software.ca.ui.CAMenu;
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
			DialogBox.comPortNotFoundMessage();
			resetActionMenu();
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
			resetActionMenu();
			return;
		}
		
		CAMenu.getActionMenu().setEnabled(true);
		CAMenu.getActionMenu().setLabel(CAMenu.ACTION_TEXT[1]);
		
		try {
			output = serialPort.getOutputStream();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	// take int to change text?
	private void resetActionMenu() {
		CAMenu.getActionMenu().setLabel(CAMenu.ACTION_TEXT[0]);
		CAMenu.getActionMenu().setEnabled(true);
	}
	
	public synchronized void serialEvent(SerialPortEvent oEvent) {
		int test = 0;
//		FileMonitor fm = new FileMonitor(new File("pom.xml"));
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				String inputLine = input.readLine();
				System.out.println("_" + inputLine + "_");
				
//				if (test == 0) {
//					test = 1;
//					output.write("r".getBytes());
//				} else {
//					output.write("y".getBytes());
//					test = 0;
//				}
//				
//				output.flush();
//				System.out.println(fm.wasModified());
				
			} catch (IOException e) {
				DialogBox.displaySystemMessage(e);
			}
		}
	}
	
	public synchronized void close() {
		// https://stackoverflow.com/questions/28766941/how-do-i-close-a-commport-in-rxtx
		System.out.println("CLOSE");
		serialPort.notifyOnDataAvailable(false);
		new Thread() {
			public void run() {
				if (serialPort != null) {
					try {
						input.close();
						output.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
//					serialPort.removeEventListener();
					serialPort.close();
//				System.out.println("YE");
//					this.destroy();
				}
			}
		}.start();
		
//		if (serialPort != null) {
//			try {
//				input.close();
//				output.close();
//			} catch (IOException e) {
//				DialogBox.displaySystemMessage(e);
//			}
//			
//			serialPort.removeEventListener();
//			serialPort.close();
//		}
		resetActionMenu();
	}

}
