package com.ngu_software.ca;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import com.ngu_software.ca.listener.KBListener;

public class JNHTest {
	

    public static void main(String[] args) {
    	try {
			GlobalScreen.registerNativeHook();
			GlobalScreen.addNativeKeyListener(new KBListener());
			
			LogManager.getLogManager().reset();

			// Get the logger for "org.jnativehook" and set the level to off.
			Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
			logger.setLevel(Level.OFF);
		}
		catch (NativeHookException ex) {
			JOptionPane.showMessageDialog(null, "here was a problem registering the native hook.\n" + ex.getMessage(), "NT Notifications", JOptionPane.ERROR_MESSAGE);
		}
    }


}
