package com.ngu_software.ca.view;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import jssc.SerialPortList;

public class DialogBox {

    private static final String TITLE = "TKJ - Code Andon";

    
    // GENERAL
    public static void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message, TITLE, 3);
    }
    
    public static void showSystemMessage(Exception e) {
        JOptionPane.showMessageDialog(null, e.toString(), TITLE, 1);
    }

    // ERROR MESSAGES
    public static void portNotSetMessage() {
        JOptionPane.showMessageDialog(null, "COM Port is null", TITLE, 1);
    }
    
    public static void noPortsAvailableMessage() {
        JOptionPane.showMessageDialog(null, "No COM Ports available", TITLE, 1);
    }
    
    public static void portFileLoadErrorMessage() {
        JOptionPane.showMessageDialog(null, "An error has occured loading last set port!", TITLE, 1);
    }

    
    // INPUTS
    public static String getPort(String currentPort) {
        String[] options = SerialPortList.getPortNames();
        return options.length == 0 ? null : (String) JOptionPane.showInputDialog(null, "Current port: " + currentPort + "\n\nSelect a COM port.", TITLE, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
    }
    
    public static File getLogFile() {
    	 JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
    	 jfc.setAcceptAllFileFilterUsed(false);
         FileNameExtensionFilter filter = new FileNameExtensionFilter("Text/Log files", "txt", "log");
         jfc.addChoosableFileFilter(filter);
         int returnValue = jfc.showOpenDialog(null);

         if (returnValue == JFileChooser.APPROVE_OPTION) {
             File selectedFile = jfc.getSelectedFile();
             return jfc.getSelectedFile();
         }
         
         return null;
    }


}
