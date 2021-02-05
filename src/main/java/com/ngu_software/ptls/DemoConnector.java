package com.ngu_software.ptls;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Scanner;

import com.ngu_software.ptls.ui.DialogBox;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

public class DemoConnector implements SerialPortEventListener {

	private SerialPort serialPort;
	
	private BufferedReader input;
	private OutputStream output;
	private static final int TIME_OUT = 2000;
	private static final int BALD_DATA_RATE = 9600;
	
	private final String PORT_KEY = "gnu.io.rxtx.SerialPorts";
	private final String[] PORTS = {"/dev/tty.usbserial-AQ016BUL", "/dev/tty.usbmodem14201"};
	
	public void initialize() {
		System.setProperty(PORT_KEY, PORTS[0]);
		CommPortIdentifier portId = null;
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

		// First, Find an instance of serial port as set in PORT_NAMES.
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
			System.out.println(currPortId.getName());
			if (currPortId.getName().equals(PORTS[0])) {
				portId = currPortId;
				break;
			}
		}
		
		if (portId == null) {
			System.out.println("Could not find COM port.");
			DialogBox.COM_ERROR();
			return;
		}
		
		try {
			serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);
			serialPort.setSerialPortParams(BALD_DATA_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

			input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));

			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}
	
	public static int test = -999;
	
	public synchronized void serialEvent(SerialPortEvent oEvent) {
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
