package com.ngu_software.ca.model;

import com.ngu_software.ca.view.DialogBox;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class CARobot {
    private Robot robot;
    private static final int STABLE_DELAY = 100;
    private static final int FAST_DELAY = 25;

    public CARobot() {
        try {
            robot = new Robot();
            robot.setAutoDelay(STABLE_DELAY);
        } catch (AWTException e) {
            DialogBox.showSystemMessage(e);
        }
    }

    public LogState anaylse(String key) {
        if (key.equals("[INFO]")) {
            return LogState.SUCCESS;
        } else if (key.equals("[WARNING]")) {
            return LogState.WARNING;
        } else if (key.equals("[ERROR]")) {
            return LogState.ERROR;
        }
        return null;
    }

    public String getJsResults() throws IOException, UnsupportedFlavorException {
        robot.setAutoDelay(STABLE_DELAY);
        // Get Browser Address Bar CTRL + L and send javascript: event
        sendKey(KeyEvent.VK_CONTROL, KeyEvent.VK_L); // Change for MacOS. Check system.

        robot.setAutoDelay(FAST_DELAY);
        sendKey(KeyEvent.VK_J);
        setJsClipboard();

        // Get back clipboard results
        sendKey(KeyEvent.VK_CONTROL, KeyEvent.VK_C);
        sendKey(KeyEvent.VK_ESCAPE);

        robot.mouseMove(0, 0);
        robot.mouseMove(900, 62);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);

        return (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
    }

    private void setJsClipboard() {
        String text = "avascript:function copyText(){var n=document.documentElement.innerHTML,o=\"N/A\";o=n.includes(\"Compile Error\")||n.includes('<div class=\"error__2Ft1\">Wrong Answer</div>')?\"[ERROR]\":n.includes('<div class=\"wrong-answer__6zc1\">Wrong Answer</div>')?\"[WARNING]\":\"[INFO]\",window.prompt(\"Copy to clipboard\",o)}copyText();";
        StringSelection stringSelection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, stringSelection);

        sendKey(KeyEvent.VK_CONTROL, KeyEvent.VK_V);
        sendKey(KeyEvent.VK_ENTER);
    }

    private void sendKey(int... keys) {
        for (Integer key : keys) {
            robot.keyPress(key);
        }

        for (Integer key : keys) {
            robot.keyRelease(key);
        }
    }

}
