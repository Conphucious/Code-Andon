package com.ngu_software.ca.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import com.ngu_software.ca.view.DialogBox;

public class LogMonitor {

	private File file;
	private long fileLastModified;

	public LogMonitor(File file) {
		this.file = file;
		clearFileContents();
	}

	public boolean wasModified() {
		return file.lastModified() != fileLastModified;
	}

	public void updateFile() {
		this.fileLastModified = file.lastModified();
	}

	public boolean isError() {
		return getCriteria() == LogState.ERROR;
	}

	public boolean isWarning() {
		return getCriteria() == LogState.WARNING;
	}

	public boolean isSuccessful() {
		return getCriteria() == LogState.SUCCESS;
	}

	public void clearFileContents() {
		PrintWriter writer;
		try {
			writer = new PrintWriter(file);
			writer.print("");
			writer.close();
			updateFile();
		} catch (FileNotFoundException e) {
			DialogBox.showSystemMessage(e);
		}
	}

	private LogState getCriteria() {
		Scanner scanner = null;
		int warnings = 0;

		try {
			updateFile();
			scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line.trim().startsWith("[ERROR]")) {
					return LogState.ERROR;
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

		return warnings > 0 ? LogState.WARNING : LogState.SUCCESS;
	}

}
