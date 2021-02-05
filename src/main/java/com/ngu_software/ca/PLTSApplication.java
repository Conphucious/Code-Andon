package com.ngu_software.ca;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.ngu_software.ca.controller.ArduinoResolver;

public class PLTSApplication {

	public static void main(String[] args) throws AWTException {
		TrayIcon ti = new TrayIcon(new ImageIcon("splash.jpg").getImage());
		ti.setImageAutoSize(true);

		PopupMenu menu = new PopupMenu();
		MenuItem menuItemOpen = new MenuItem("Set COM Port");
		MenuItem menuItemOpen4 = new MenuItem("Start Driver");				// change this to stop once it runs
		MenuItem menuItemOpen2 = new MenuItem("Debug Serial Messages");
		MenuItem menuItemOpen3 = new MenuItem("About");
		MenuItem menuItemExit = new MenuItem("Exit");

		menu.add(menuItemOpen4);
		menu.add(menuItemOpen);
		menu.add(menuItemOpen2);
		menu.add(menuItemOpen3);
		menu.add(menuItemExit);
		ti.setPopupMenu(menu);

		menuItemExit.addActionListener(e -> {
			System.exit(0);
		});

		menuItemOpen4.addActionListener(e -> {
			ArduinoResolver ar = new ArduinoResolver("dev/tty.E35BT-JL_SPP-4");
		});

		menuItemOpen.addActionListener(e -> {
			JOptionPane.showMessageDialog(null, "java is fun", "Title", 1);
//			Object[] possibilities = {"ham", "spam", "yam"};
//			String s = (String)JOptionPane.showInputDialog(
//			                    frame,
//			                    "Complete the sentence:\n"
//			                    + "\"Green eggs and...\"",
//			                    "Customized Dialog",
//			                    JOptionPane.PLAIN_MESSAGE,
//			                    icon,
//			                    possibilities,
//			                    "ham");
//
//			//If a string was returned, say so.
//			if ((s != null) && (s.length() > 0)) {
////			    setLabel("Green eggs and... " + s + "!");
//			    return;
//			}

		});
		// CMD SHIFT TILDA WILL RUN CODE
		// CMD SHIFT 1 WILL SUBMIT CODE

		SystemTray.getSystemTray().add(ti);
	}

	private static void runer() {
		DemoConnector ad = new DemoConnector();
		ad.initialize();
		Thread t = new Thread() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException ie) {

					}
				}
			}
		};
		t.start();
		System.out.println("Started");
	}

}
