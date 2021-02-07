package com.ngu_software.ca.ui;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.ngu_software.ca.AResolver;
import com.ngu_software.ca.controller.ArduinoResolver;

import jssc.SerialPortList;

public class CAMenu {

	private TrayIcon trayIcon;
	private PopupMenu menu;
	private static MenuItem miAction; // changes to start/stop too
	private MenuItem miSetComPort; // /dev/tty.usbmodem14201 UNO
	private MenuItem miAbout;
	private MenuItem miExit;

	private String port = "/dev/tty.usbmodem14101";
	public static final String[] ACTION_TEXT = { "Start", "Stop" };
	private ArduinoResolver arduinoResolver;
	
	private AResolver ar;

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
		
		arduinoResolver = null;
	}

	private void populate() {
		menu.add(miAction);
		
//		PopupMenu mis = new PopupMenu();
//		mis.add(new MenuItem("asd"));
//		SerialPortList.getPortNames()

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
					arduinoResolver = new ArduinoResolver(port);
				}
			} else if (arduinoResolver != null && miAction.getLabel().equals(ACTION_TEXT[1])) {
				miAction.setEnabled(false);
				arduinoResolver.close();
				miAction.setEnabled(true);
			}
		});

		miSetComPort.addActionListener(e -> {
//			String portInput = DialogBox.getInput("Enter a COM Port (current value is: " + port + ")");
//			if (portInput != null && !portInput.trim().isEmpty()) {
//				port = portInput;
//			}
			
			String[] options = SerialPortList.getPortNames();
			String n = (String)JOptionPane.showInputDialog(null, "Do you like turtles??", "I like turtles", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
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
