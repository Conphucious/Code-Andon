package com.ngu_software.ca.ui;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.ngu_software.ca.controller.ArduinoResolver;

public class CAMenu {

	private TrayIcon trayIcon;
	private PopupMenu menu;
	private MenuItem miAction; // changes to start/stop too
	private MenuItem miSetComPort;
	private MenuItem miDebugMessages;
	private MenuItem miAbout;
	private MenuItem miExit;
	
	private String port;

	public CAMenu() {
		initialize();
		populate();
		setEvents();
		setProperties();
	}

	private void initialize() {
		trayIcon = new TrayIcon(new ImageIcon("splash.jpg").getImage());
		menu = new PopupMenu();
		miAction = new MenuItem("Start"); // change this to stop once it runs
		miSetComPort = new MenuItem("Set COM Port");
		miDebugMessages = new MenuItem("Debug Serial Messages");
		miAbout = new MenuItem("About");
		miExit = new MenuItem("Exit");
	}

	private void populate() {
		menu.add(miAction);
		menu.add(miSetComPort);
		menu.add(miDebugMessages);
		menu.add(miAbout);
		menu.add(miExit);
	}

	private void setEvents() {
		miAction.addActionListener(e -> {
			if (port == null || port.isEmpty()) {
				DialogBox.unsetPortMessage();
			} else {
				ArduinoResolver ar = new ArduinoResolver(port);
			}
		});

		miSetComPort.addActionListener(e -> {
			String portInput = DialogBox.getInput("Enter a COM Port (current value is: " + port + ")");
			if (portInput != null && !portInput.trim().isEmpty()) {
				port = portInput;
			}
		});

		miDebugMessages.addActionListener(e -> {

		});

		miAbout.addActionListener(e -> {

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

}
