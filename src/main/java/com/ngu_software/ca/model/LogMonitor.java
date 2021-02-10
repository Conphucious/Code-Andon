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

	public boolean wasModified() {
		return file.lastModified() != fileLastModified;
	}

	public void updateFile() {
		this.fileLastModified = file.lastModified();
	}

	public boolean isError() {
		return true;
	}

	public boolean isWarning() {
		return true;
	}

	public boolean isSuccessful() {
		return !wasModified() || getCriteria() == CAState.WARNING;
	}

	private CAState getCriteria() {
		Scanner scanner;
		try {
			scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				System.out.println(scanner.nextLine());
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			DialogBox.showSystemMessage(e);
		}

		return CAState.SUCCESS;
	}

}
