package com.ngu_software.ca.model;

import java.io.Serializable;

public class CAProperties implements Serializable { 

	private static final long serialVersionUID = 1L;
	private String comPort, buildLogFile, runtimeLogFile;
	
	public CAProperties(String comPort, String buildLogFile, String runtimeLogFile) {
		this.comPort = comPort;
		this.buildLogFile = buildLogFile;
		this.runtimeLogFile = runtimeLogFile;
	}
	
	public CAProperties() {
		this.comPort = "";
		this.buildLogFile = "";
		this.runtimeLogFile = "";
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
