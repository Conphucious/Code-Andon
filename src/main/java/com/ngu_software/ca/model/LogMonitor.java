package com.ngu_software.ca.model;

import java.io.File;

public class LogMonitor {
	
	private File file;
	private long fileLastModified;
	
	public LogMonitor(File file) {
		this.file = file;
		fileLastModified = file.lastModified();
	}
	
	public boolean wasModified() {
		return file.lastModified() != fileLastModified;
	}
	
	public void updateFile() {
		this.fileLastModified = file.lastModified();
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
}
