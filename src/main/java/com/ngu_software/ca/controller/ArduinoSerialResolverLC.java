package com.ngu_software.ca.controller;

import com.ngu_software.ca.model.CARobot;
import com.ngu_software.ca.model.LogMonitor;
import com.ngu_software.ca.model.LogState;
import com.ngu_software.ca.model.SerialCommand;
import com.ngu_software.ca.view.CAMenu;
import com.ngu_software.ca.view.DialogBox;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.awt.*;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class ArduinoSerialResolverLC implements SerialPortEventListener, NativeKeyListener {

    private SerialPort port;
    private StringBuilder message = new StringBuilder();
    private boolean startingToken = false;
    private boolean isKeyPressed = false;
    private LogState leetCodeState = null;

    public ArduinoSerialResolverLC(String comPort) {
        try {
            port = new SerialPort(comPort);
            port.openPort();
            port.setParams(9600, 8, 1, 0);
            port.setEventsMask(SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR);
            port.addEventListener(this);
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(this);
            LogManager.getLogManager().reset();
            Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
            logger.setLevel(Level.OFF);
        } catch (SerialPortException e) {
            DialogBox.showSystemMessage(e);
            return;
        } catch (NativeHookException e) {
            DialogBox.showSystemMessage(e);
            return;
        }
        CAMenu.toggleLcActionMenuItem();
    }

    public void serialEvent(SerialPortEvent event) {
        if (event.isRXCHAR() && event.getEventValue() > 0) {
            try {
                byte buffer[] = port.readBytes();
                for (byte b : buffer) {
                    if (b == '#') {
                        startingToken = true;
                        message.setLength(0);
                    } else if (startingToken == true) {
                        if (b == '!') {
                            startingToken = false;
                            if (isKeyPressed) {
                                if (leetCodeState == LogState.SUCCESS) {
                                    port.writeString(SerialCommand.TX_GREEN_LIGHT.getValue());
                                } else if (leetCodeState == LogState.WARNING) {
                                    port.writeString(SerialCommand.TX_YELLOW_LIGHT.getValue());
                                } else if (leetCodeState == LogState.ERROR) {
                                    port.writeString(SerialCommand.TX_RED_LIGHT.getValue());
                                }
                            }
                            if (message.toString().trim().equals(SerialCommand.TX_RESET_PRESSED.getValue())) {
                                isKeyPressed = false;
                            }
                        } else {
                            message.append((char) b);
                        }
                    }
                }
            } catch (SerialPortException e) {
                DialogBox.showSystemMessage(e);
            }
        }
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_ALT_R) {
            isKeyPressed = true;
            try {
                CARobot roberto = new CARobot();
                String result = roberto.getJsResults();
                leetCodeState = roberto.anaylse(result);
            } catch (IOException e1) {
                DialogBox.showSystemMessage(e1);
            } catch (UnsupportedFlavorException e2) {
                DialogBox.showSystemMessage(e2);
            }
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {

    }

    public void close() {
        try {
            if (port.isOpened()) {
                port.closePort();
            }
            GlobalScreen.unregisterNativeHook();
            GlobalScreen.removeNativeKeyListener(this);
            CAMenu.toggleLcActionMenuItem();
        } catch (SerialPortException e) {
            DialogBox.showSystemMessage(e);
        } catch (NativeHookException e) {
            DialogBox.showSystemMessage(e);
        }
    }

}
