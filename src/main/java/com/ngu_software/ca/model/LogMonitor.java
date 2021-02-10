package com.ngu_software.ca.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.ngu_software.ca.view.DialogBox;

public class LogMonitor {

	private File file;
	private long fileLastModified;

	public LogMonitor(File file) {
		this.file = file;
		fileLastModified = file.lastModified();
	}

	// Use modified in ArduinoSerialResolver section
	public boolean wasModified() {
		return file.lastModified() != fileLastModified;
	}

	public void updateFile() {
		this.fileLastModified = file.lastModified();
	}

	public boolean isError() {
		return getCriteria() == CAState.ERROR;
	}

	public boolean isWarning() {
		return getCriteria() == CAState.WARNING;
	}

	public boolean isSuccessful() {
		return getCriteria() == CAState.SUCCESS;
	}

	private CAState getCriteria() {
		Scanner scanner = null;
		int warnings = 0;
		
		try {
			scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line.trim().startsWith("[ERROR]")) {
					return CAState.ERROR;
				} else if (line.trim().startsWith("[WARNING]")) {
					warnings++;
				}
			}
		} catch (FileNotFoundException e) {
			DialogBox.showSystemMessage(e);
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}
		
		return warnings > 0 ? CAState.WARNING : CAState.SUCCESS;
	}

}
