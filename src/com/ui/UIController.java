package com.ui;

import com.core.IReturnMessage;
import com.core.ReturnMessage;
import groovy.util.Eval;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.codehaus.groovy.control.CompilationFailedException;
import org.testng.reporters.Files;

import java.io.File;
import java.io.IOException;

public class UIController {
    @FXML ConsoleTextArea console;

    private IReturnMessage execute(String commands) {
        IReturnMessage message = new ReturnMessage();
        try {
            message.appendInfo("Result: " + Eval.me(commands));
        } catch (CompilationFailedException e) {
            message.appendErrors(e.getMessage());
        }
        return message;
    }

    public void execute() {
        String commands = ""; // TODO
        IReturnMessage message = execute(commands);
        //TODO message;
    }

    public void runScript() {
        IReturnMessage message = new ReturnMessage();
        FileChooser fileChooser = new FileChooser();
        Stage stage = new Stage();
        File file = fileChooser.showOpenDialog(stage);
        if(file!=null) {
            try {
                message.appendInfo("Read from " + file.getName() + ".");
                message.append(execute(Files.readFile(file)));
            } catch (IOException e) {
                message.appendErrors(e.getMessage());
            }
        } else {
            message.appendErrors("No file selected.");
        }
        //TODO message;
    }

    public void saveScript() {
        String commands = console.getText(); // TODO
        IReturnMessage message = new ReturnMessage();
        FileChooser fileChooser = new FileChooser();
        Stage stage = new Stage();
        File file = fileChooser.showSaveDialog(stage);
        if(file!=null) {
            try {
                Files.writeFile(commands, file);
                message.appendInfo("Written to " + file.getName() + ".");
            } catch (IOException e) {
                message.appendErrors(e.getMessage());
            }
        } else {
            message.appendErrors("No file selected.");
        }
        //TODO message;
    }
}
