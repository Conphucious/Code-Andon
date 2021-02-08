package com.ngu_software.ca.model;

public class CAProperties { // make this serializable and save bin obj rather than txt for comPort and load back whole file. 
	
	private String comPort, buildLogFile, runtimeLogFile; // make log files a File or String?
	
	public CAProperties(String comPort, String buildLogFile, String runtimeLogFile) {
		this.comPort = comPort;
		this.buildLogFile = buildLogFile;
		this.runtimeLogFile = runtimeLogFile;
	}

	public String getComPort() {
		return comPort;
	}

	public void setComPort(String comPort) {
		this.comPort = comPort;
	}

	public String getBuildLogFile() {
		return buildLogFile;
	}

	public void setBuildLogFile(String buildLogFile) {
		this.buildLogFile = buildLogFile;
	}

	public String getRuntimeLogFile() {
		return runtimeLogFile;
	}

	public void setRuntimeLogFile(String runtimeLogFile) {
		this.runtimeLogFile = runtimeLogFile;
	}
	
}
