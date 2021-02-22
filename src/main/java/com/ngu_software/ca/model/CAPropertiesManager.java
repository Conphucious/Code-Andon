package com.ngu_software.ca.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.ngu_software.ca.view.DialogBox;

public class CAPropertiesManager implements Serializable {

	private static final long serialVersionUID = 1L;
	private File file;
	private static final String FILE_PATH = "application.capf";

	private CAProperties props;

	public CAPropertiesManager() {
		props = new CAProperties();
		file = new File(FILE_PATH);
	}
	
	public CAProperties getProps() {
		return props;
	}

	public void saveComPort(String port) {
		props.setComPort(port);
		save();
	}

	public void saveBuildLogFile(String buildLogFile) {
		props.setBuildLogFile(buildLogFile);
		save();
	}

	public void saveRuntimeLogFile(String runtimeLogFile) {
		props.setRuntimeLogFile(runtimeLogFile);
		save();
	}

	private void save() {
		FileOutputStream fileOutputStream;
		try {
			fileOutputStream = new FileOutputStream(FILE_PATH);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(props);
			objectOutputStream.close();
		} catch (FileNotFoundException e) {
			DialogBox.showSystemMessage(e);
		} catch (IOException e) {
			DialogBox.showSystemMessage(e);
		}
	}

	public void loadProperties() {
		if (!file.exists()) {
			return;
		}

		FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream(FILE_PATH);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			props = (CAProperties) objectInputStream.readObject();
			objectInputStream.close();
		} catch (FileNotFoundException e) {
			DialogBox.showSystemMessage(e);
		} catch (IOException e) {
			DialogBox.showSystemMessage(e);
		} catch (ClassNotFoundException e) {
			DialogBox.showSystemMessage(e);
		}
	}

}
