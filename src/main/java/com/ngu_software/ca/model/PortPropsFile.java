package com.ngu_software.ca.model;

import com.ngu_software.ca.view.DialogBox;

import java.io.*;

public class PortPropsFile {

    private File file;
    private static final String FILE_PATH = "port.sys";

    public PortPropsFile() {
        file = new File(FILE_PATH);
    }

    public void savePort(String port) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            fileWriter.write(port);
            fileWriter.close();
        } catch (IOException e) {
            DialogBox.displaySystemMessage(e);
        }
    }

    public String retrievePort() {
        if (!file.exists()) {
            file = new File(FILE_PATH);
            return null;
        }

        String line = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH));
            line = bufferedReader.readLine();

            if (line == null) {
                DialogBox.portFileLoadErrorMessage();
                line = null;
            } else if (line.trim().isEmpty()) {
                line = null;
            }

            bufferedReader.close();
        } catch (FileNotFoundException e) {
            DialogBox.displaySystemMessage(e);
        } catch (IOException e) {
            DialogBox.displaySystemMessage(e);
        }

        return line;
    }

}
