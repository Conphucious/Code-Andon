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

public class ArduinoSerialResolver implements SerialPortEventListener {

	private SerialPort port;
	private LogMonitor lmBuildCompile, lmRuntime;
	
	private StringBuilder message = new StringBuilder();
	private Boolean startingToken = false;

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
		if(event.isRXCHAR() && event.getEventValue() > 0){
	        try {
	            byte buffer[] = port.readBytes();
	            for (byte b: buffer) {
	                if (b == '#') {
	                    startingToken = true;
	                    message.setLength(0);
	                }
	                else if (startingToken == true) {
	                    if (b == '!') {
	                        startingToken = false;
	                        System.out.println("> " + message.toString().trim());
	                        if (lmBuildCompile != null && lmRuntime != null) {
	    						if (lmBuildCompile.isSuccessful() && lmRuntime.isSuccessful()) {
	    							port.writeString(SerialCommand.TX_GREEN_LIGHT.getValue());
	    						} else if (lmBuildCompile.isError() || lmRuntime.isError()) {
	    							port.writeString(SerialCommand.TX_RED_LIGHT.getValue());
	    						} else if (lmBuildCompile.isWarning() || lmRuntime.isWarning()) {
	    							port.writeString(SerialCommand.TX_YELLOW_LIGHT.getValue());
	    						}
	    					}
	                    }
	                    else {
	                        message.append((char)b);
	                    }
	                }
	            }                
	        }
	        catch (SerialPortException e) {
	            DialogBox.showSystemMessage(e);
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
