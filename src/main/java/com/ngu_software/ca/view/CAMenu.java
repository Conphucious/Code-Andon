package com.ngu_software.ca.view;

import com.ngu_software.ca.controller.ArduinoSerialResolver;
import com.ngu_software.ca.model.PortPropsFile;

import javax.swing.*;
import java.awt.*;

public class CAMenu {

	private TrayIcon trayIcon;
	private PopupMenu menu;
	private static MenuItem miAction;
	private MenuItem miSetPort, miSetBuildFile, miSetRuntimeFile;
	private MenuItem miAbout;
	private MenuItem miExit;

	private static String port = "null";
	public static final String[] ACTION_TEXT = { "Start", "Stop" };
	private ArduinoSerialResolver arduinoSerialResolver;
	private PortPropsFile portPropsFile;

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
		miAbout = new MenuItem("About");
		miExit = new MenuItem("Exit");
		portPropsFile = new PortPropsFile();
		port = portPropsFile.retrievePort();
	}

	private void populate() {
		menu.add(miAction);
		menu.add(miSetPort);
		menu.add(miSetBuildFile);
		menu.add(miSetRuntimeFile);
		menu.add(miAbout);
		menu.add(miExit);
	}

	private void setEvents() {
		miAction.addActionListener(e -> {							// need to check log files here too.
			if (miAction.getLabel().equals(ACTION_TEXT[0])) {
				miAction.setEnabled(false);
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
			port = DialogBox.getPort(port);
			if (port == null) {
				DialogBox.noPortsAvailableMessage();
			} else {
				portPropsFile.savePort(port);
			}
			setOptionVisiblity(true);
		});

		miSetBuildFile.addActionListener(e -> {
			DialogBox.getLogFile();
		});

		miSetRuntimeFile.addActionListener(e -> {
			DialogBox.getLogFile();
		});

		miAbout.addActionListener(e -> {
			setOptionVisiblity(false);
			DialogBox.aboutMessage();
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
			DialogBox.displaySystemMessage(e);
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
