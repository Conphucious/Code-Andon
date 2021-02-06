package com.ngu_software.ca;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class Test {

	public static void main(String[] args) {
//		String[] ports = SerialPortList.getPortNames();
//		for(int i = 0; i < ports.length; i++) {
//			System.out.println(ports[i]);
//			SerialPort port = new SerialPort(ports[i]);
//			try {
//				port.openPort();
//				port.setParams(9600,  8, 1, 0);
//				String buffer = port.readString();
////				port.writeBytes("Testing serial from Java".getBytes());
//				System.out.println(buffer);
//				port.closePort();
//			} catch (SerialPortException e) {
//				e.printStackTrace();
//			}
//		}
		
		AResolver ar = new AResolver();
		
		
	}

}
