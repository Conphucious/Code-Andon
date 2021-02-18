package com.ngu_software.ca.view;

import com.ngu_software.ca.controller.ArduinoSerialResolverIDE;
import com.ngu_software.ca.controller.ArduinoSerialResolverLC;
import com.ngu_software.ca.model.CAPropertiesManager;
import jssc.SerialPortList;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class CAMenu {

	private TrayIcon trayIcon;
	private PopupMenu menu;
	private static MenuItem miActionIde, miActionLeetCode;
	private MenuItem miSetPort, miSetBuildFile, miSetRuntimeFile;
	private MenuItem miInformation;
	private MenuItem miExit;

	public static final String[] ACTION_TEXT_IDE = { "Start IDE Listener", "Stop IDE Listener" };
	public static final String[] ACTION_TEXT_LC = { "Start LeetCode Listener", "Stop LeetCode Listener" };
	private ArduinoSerialResolverIDE arduinoSerialResolverIde;
	private ArduinoSerialResolverLC arduinoSerialResolverLc;
	private CAPropertiesManager propsManager;

	public CAMenu() {
		initialize();
		populate();
		setEvents();
		setProperties();
	}

	private void initialize() {
		trayIcon = new TrayIcon(new ImageIcon("./icon.png").getImage());
		menu = new PopupMenu();
		miActionIde = new MenuItem(ACTION_TEXT_IDE[0]);
		miActionLeetCode = new MenuItem(ACTION_TEXT_LC[0]);
		miSetPort = new MenuItem("Set COM Arduino Port");
		miSetBuildFile = new MenuItem("Set Build Log File");
		miSetRuntimeFile = new MenuItem("Set Runtime Log File");
		miInformation = new MenuItem("Config Information");
		miExit = new MenuItem("Exit");
		
		propsManager = new CAPropertiesManager();
		propsManager.loadProperties();
	}

	private void populate() {
		menu.add(miActionIde);
		menu.add(miActionLeetCode);
		menu.add(miSetPort);
		menu.add(miSetBuildFile);
		menu.add(miSetRuntimeFile);
		menu.add(miInformation);
		menu.add(miExit);
	}

	private void setEvents() {
		miActionIde.addActionListener(e -> {
			if (miActionIde.getLabel().equals(ACTION_TEXT_IDE[0])) {
				miActionIde.setEnabled(false);
				String port = propsManager.getProps().getComPort();
				String buildLogFile = propsManager.getProps().getBuildLogFile();
				String runtimeLogFile = propsManager.getProps().getRuntimeLogFile();
				if (port == null || port.trim().isEmpty()) {
					DialogBox.errorPortNotSetMessage();
				} else if (buildLogFile == null || runtimeLogFile == null) {
					DialogBox.errorLogFilesNullMessage();
				} else {
					arduinoSerialResolverIde = new ArduinoSerialResolverIDE(port, buildLogFile, runtimeLogFile);
				}
			} else if (miActionIde.getLabel().equals(ACTION_TEXT_IDE[1])) {
				arduinoSerialResolverIde.resetFiles();
				arduinoSerialResolverIde.close();
			}
			miActionIde.setEnabled(true);
		});

		miActionLeetCode.addActionListener(e -> {
			if (miActionLeetCode.getLabel().equals(ACTION_TEXT_LC[0])) {
				String port = propsManager.getProps().getComPort();
				arduinoSerialResolverLc = new ArduinoSerialResolverLC(port);
			} else {
				arduinoSerialResolverLc.close();
			}
		});

		miSetPort.addActionListener(e -> {
			setOptionVisiblity(false);
			String port = DialogBox.getPort(propsManager.getProps().getComPort());
			if (!portsAvailable() && port == null) {
				DialogBox.errorNoPortsAvailableMessage();
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
			if (propsManager.getProps() != null) {
				
			}
			DialogBox.showMessage("Current Port: " + propsManager.getProps().getComPort() + "\n" 
					+ "Build Log File: " + propsManager.getProps().getBuildLogFile() + "\n" 
					+ "Runtime Log File: " + propsManager.getProps().getRuntimeLogFile() + "\n\n" 
					+ "About: https://github.com/Conphucious");
			setOptionVisiblity(true);
		});

		miExit.addActionListener(e -> {
			if (arduinoSerialResolverIde != null) {
				arduinoSerialResolverIde.close();
			}
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

	private boolean portsAvailable() {
		return SerialPortList.getPortNames().length > 0;
	}

	private void setOptionVisiblity(boolean isShown) {
		for (int i = 0; i < menu.getItemCount() - 1; i++) {
			menu.getItem(i).setEnabled(isShown);
		}
	}

	public static void toggleIdeActionMenuItem() {
		String action = miActionIde.getLabel().equals(ACTION_TEXT_IDE[0]) ? ACTION_TEXT_IDE[1] : ACTION_TEXT_IDE[0];
		miActionIde.setLabel(action);
	}
	public static void toggleLcActionMenuItem() {
		String action = miActionLeetCode.getLabel().equals(ACTION_TEXT_LC[0]) ? ACTION_TEXT_LC[1] : ACTION_TEXT_LC[0];
		miActionLeetCode.setLabel(action);
	}

}
