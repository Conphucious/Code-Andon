package com.ngu_software.ca.view;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.io.File;

import javax.swing.ImageIcon;

import com.ngu_software.ca.controller.ArduinoSerialResolver;
import com.ngu_software.ca.model.CAPropertiesManager;

public class CAMenu {

	private TrayIcon trayIcon;
	private PopupMenu menu;
	private static MenuItem miAction;
	private MenuItem miSetPort, miSetBuildFile, miSetRuntimeFile;
	private MenuItem miInformation;
	private MenuItem miExit;

	public static final String[] ACTION_TEXT = { "Start", "Stop" };
	private ArduinoSerialResolver arduinoSerialResolver;
	private CAPropertiesManager propsManager;

	public CAMenu() {
		initialize(); // load previous port by saving to file and loading?
		populate();
		setEvents();
		setProperties();
	}

	private void initialize() {
		trayIcon = new TrayIcon(new ImageIcon("icon.png").getImage());
		menu = new PopupMenu();
		miAction = new MenuItem(ACTION_TEXT[0]);
		miSetPort = new MenuItem("Set COM Arduino Port");
		miSetBuildFile = new MenuItem("Set Build Log File");
		miSetRuntimeFile = new MenuItem("Set Runtime Log File");
		miInformation = new MenuItem("Config Information");
		miExit = new MenuItem("Exit");
		
		propsManager = new CAPropertiesManager();
		propsManager.loadProperties();
	}

	private void populate() {
		menu.add(miAction);
		menu.add(miSetPort);
		menu.add(miSetBuildFile);
		menu.add(miSetRuntimeFile);
		menu.add(miInformation);
		menu.add(miExit);
	}

	private void setEvents() {
		miAction.addActionListener(e -> { // need to check log files here too.
			if (miAction.getLabel().equals(ACTION_TEXT[0])) {
				miAction.setEnabled(false);

				String port = propsManager.getProps().getComPort();
				if (port == null || port.trim().isEmpty()) {
					DialogBox.portNotSetMessage();
				} else {
					arduinoSerialResolver = new ArduinoSerialResolver(port);
				}
			} else if (miAction.getLabel().equals(ACTION_TEXT[1])) {
				arduinoSerialResolver.close();
			}
			miAction.setEnabled(true);
		});

		miSetPort.addActionListener(e -> {
			setOptionVisiblity(false);
			String port = DialogBox.getPort(propsManager.getProps().getComPort());
			if (port == null) {
				DialogBox.noPortsAvailableMessage();
			} else {
				propsManager.saveComPort(port);
			}
			setOptionVisiblity(true);
		});

		miSetBuildFile.addActionListener(e -> {
			File buildLogFile = DialogBox.getLogFile();

			if (buildLogFile != null && buildLogFile.exists()) {
				propsManager.saveBuildLogFile(buildLogFile.toString());
			}
		});

		miSetRuntimeFile.addActionListener(e -> {
			File runtimeLogFile = DialogBox.getLogFile();

			if (runtimeLogFile != null && runtimeLogFile.exists()) {
				propsManager.saveRuntimeLogFile(runtimeLogFile.toString());
			}
		});

		miInformation.addActionListener(e -> {
			setOptionVisiblity(false);
			DialogBox.showMessage("Current Port: " + propsManager.getProps().getComPort() + "\n" 
					+ "Build Log File: " + propsManager.getProps().getBuildLogFile() + "\n" 
					+ "Runtime Log File: " + propsManager.getProps().getRuntimeLogFile() + "\n\n" 
					+ "About: https://github.com/Conphucious");
			setOptionVisiblity(true);
		});

		miExit.addActionListener(e -> {
			System.exit(0);
		});
	}

	private void setProperties() {
		trayIcon.setImageAutoSize(true);
		trayIcon.setPopupMenu(menu);
		try {
			SystemTray.getSystemTray().add(trayIcon);
		} catch (AWTException e) {
			DialogBox.showSystemMessage(e);
		}
	}

	private void setOptionVisiblity(boolean isShown) {
		for (int i = 0; i < menu.getItemCount() - 1; i++) {
			menu.getItem(i).setEnabled(isShown);
		}
	}

	public static void toggleActionMenuItem() {
		String action = miAction.getLabel().equals(ACTION_TEXT[0]) ? ACTION_TEXT[1] : ACTION_TEXT[0];
		miAction.setLabel(action);
	}

}
