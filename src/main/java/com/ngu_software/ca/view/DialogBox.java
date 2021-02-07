package com.ngu_software.ca.view;

import jssc.SerialPortList;

import javax.swing.JOptionPane;

public class DialogBox {

    private static final String TITLE = "Jimmy's Code Andon";

    public static void aboutMessage() {
        JOptionPane.showMessageDialog(null, "https://github.com/Conphucious", TITLE, 1);
    }

    public static void portNotSetMessage() {
        JOptionPane.showMessageDialog(null, "COM Port has not yet been set", TITLE, 1);
    }

    public static void portFileLoadErrorMessage() {
        JOptionPane.showMessageDialog(null, "An error has occured loading last set port!", TITLE, 1);
    }

    public static void displaySystemMessage(Exception e) {
        JOptionPane.showMessageDialog(null, e.toString(), TITLE, 1);
    }

    public static String requestSetPort(String currentPort) {
        String[] options = SerialPortList.getPortNames();
        return (String) JOptionPane.showInputDialog(null, "Current port: " + currentPort + "\n\nSelect a COM port.", TITLE, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
    }


}
