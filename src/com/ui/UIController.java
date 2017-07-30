package com.ui;

import com.core.IReturnMessage;
import com.core.ReturnMessage;
import com.UtilityFunctions;
import groovy.util.Eval;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.codehaus.groovy.control.CompilationFailedException;
import org.testng.reporters.Files;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UIController {
    @FXML
    ListView<ListCell<String>> palette;

    private class CustomListCell extends ListCell<String> {
        public CustomListCell(String name, boolean empty) {
            this.updateItem(name, empty);
        }

        @Override
        public void updateItem(String name, boolean empty) {
            super.updateItem(name, empty);
            if(empty) {
                setGraphic(null);
                setText(null);
            } else {
                // TODO: filename
                ImageView imageView = new ImageView(UtilityFunctions.getFilePath(name));
                setGraphic(imageView);
                setText(name);
            }
        }
    }

    public void initPalette() {
        File[] files = (new File(UtilityFunctions.getFilePath("sprites"))).listFiles();
        if(files != null) {
            //palette.setCellFactory(listView -> new CustomListCell());
            List<ListCell<String>> cells = Arrays.asList(files).stream().map(e -> new CustomListCell(e.getName(), false)).collect(Collectors.toList());
            palette.setItems(FXCollections.observableArrayList(cells));
        }
    }

    @FXML
    ConsoleTextArea console;

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
