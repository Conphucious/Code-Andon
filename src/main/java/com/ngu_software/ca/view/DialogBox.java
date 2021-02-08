package com.ngu_software.ca.view;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

import jssc.SerialPortList;

public class DialogBox {

    private static final String TITLE = "Jimmy's Code Andon";

    public static void aboutMessage() {
        JOptionPane.showMessageDialog(null, "https://github.com/Conphucious", TITLE, 1);
    }


    public static void portFileLoadErrorMessage() {
        JOptionPane.showMessageDialog(null, "An error has occured loading last set port!", TITLE, 1);
    }

    public static void displaySystemMessage(Exception e) {
        JOptionPane.showMessageDialog(null, e.toString(), TITLE, 1);
    }

    public static String getPort(String currentPort) {
        String[] options = SerialPortList.getPortNames();
        return options.length == 0 ? null : (String) JOptionPane.showInputDialog(null, "Current port: " + currentPort + "\n\nSelect a COM port.", TITLE, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
    }
    
    public static void portNotSetMessage() {
        JOptionPane.showMessageDialog(null, "COM Port has not yet been set", TITLE, 1);
    }
    
    public static void noPortsAvailableMessage() {
        JOptionPane.showMessageDialog(null, "No COM Ports available", TITLE, 1);
    }
    
    public static void getLogFile() {
    	 JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

         int returnValue = jfc.showOpenDialog(null);
         // int returnValue = jfc.showSaveDialog(null);

         if (returnValue == JFileChooser.APPROVE_OPTION) {
             File selectedFile = jfc.getSelectedFile();
             System.out.println(selectedFile.getAbsolutePath());
         }
    }


}
