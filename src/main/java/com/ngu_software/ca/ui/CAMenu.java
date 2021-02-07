package com.ngu_software.ca.ui;

import com.ngu_software.ca.controller.ArduinoSerialResolver;

import javax.swing.*;
import java.awt.*;

public class CAMenu {

	private TrayIcon trayIcon;
	private PopupMenu menu;
	private static MenuItem miAction; // changes to start/stop too
	private MenuItem miSetComPort; // /dev/tty.usbmodem14201 UNO
	private MenuItem miAbout;
	private MenuItem miExit;

	private static String port = "null";
	public static final String[] ACTION_TEXT = { "Start", "Stop" };
	private ArduinoSerialResolver arduinoSerialResolver;
	
	public CAMenu() {
		// load previous port by saving to file and loading?
		initialize();
		populate();
		setEvents();
		setProperties();
	}

	private void initialize() {
		trayIcon = new TrayIcon(new ImageIcon("splash.jpg").getImage());
		menu = new PopupMenu();
		miAction = new MenuItem(ACTION_TEXT[0]); // change this to stop once it runs
		miSetComPort = new MenuItem("Set COM Port");
		miAbout = new MenuItem("About");
		miExit = new MenuItem("Exit");
	}

	private void populate() {
		menu.add(miAction);
		menu.add(miSetComPort);
		menu.add(miAbout);
		menu.add(miExit);
	}

	private void setEvents() {
		miAction.addActionListener(e -> {
			if (miAction.getLabel().equals(ACTION_TEXT[0])) {
				if (port == null || port.isEmpty()) {
					miAction.setEnabled(false);				// maybe need to disable all options but exit? for fail safe in case.
					DialogBox.comPortNotSetMessage();
					miAction.setEnabled(true);
				} else {
					miAction.setEnabled(false);
					arduinoSerialResolver = new ArduinoSerialResolver(port);
				}
			} else if (arduinoSerialResolver != null && miAction.getLabel().equals(ACTION_TEXT[1])) {
				miAction.setEnabled(false);
				arduinoSerialResolver.close();
				miAction.setEnabled(true);
			}
		});

		miSetComPort.addActionListener(e -> {
			port = DialogBox.requestSetPort(port);
		});

		miAbout.addActionListener(e -> {
			DialogBox.aboutMessage();
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

	public static MenuItem getActionMenu() {
		return miAction;
	}

}
