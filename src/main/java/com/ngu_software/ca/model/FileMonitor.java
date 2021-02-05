package com.ngu_software.ca.model;

import java.io.File;

public class FileMonitor {
	
	private File file;
	private long fileLastModified;
	
	public FileMonitor(File file) {
		this.file = file;
		fileLastModified = file.lastModified();
	}
	
	public boolean wasModified() {
		return file.lastModified() != fileLastModified;
	}
	
	public void updateFile() {
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
}
