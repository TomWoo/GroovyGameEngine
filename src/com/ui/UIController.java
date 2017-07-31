package com.ui;

import com.core.Controller;
import com.core.IReturnMessage;
import com.core.ReturnMessage;
import com.UtilityFunctions;
import groovy.lang.GroovyShell;
import groovy.util.Eval;
import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
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
    @FXML ListView<String> palette;
    @FXML ConsoleTextArea console;

    private Controller controller = new Controller(false);
    private GroovyShell groovyShell = new GroovyShell();

    @FXML
    public void initialize() {
        initPalette();
        initConsole();

        // Game loop
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                controller.update(now);
            }
        };
        gameLoop.start();
    }

    // Palette

    private void initPalette() { // TODO: refactor
        palette.setCellFactory(listCell -> new ListCell<String>() {
            @Override
            public void updateItem(String name, boolean empty) {
                super.updateItem(name, empty);
                if(empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    ImageView imageView;
                    try {
                        imageView = new ImageView(UtilityFunctions.getResourceURL("sprites/" + name).toString());
                        imageView.setPreserveRatio(true);
                        imageView.setFitHeight(16.0);
                        setGraphic(imageView);
                        setText(name);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        });

        File[] files = (UtilityFunctions.getResourceFile("sprites")).listFiles();
        if(files != null) {
            List<String> cells = Arrays.asList(files).stream().map(e -> {
                String[] filePathArr = e.getAbsolutePath().split("\\\\");
                return filePathArr[filePathArr.length-1];
            }).collect(Collectors.toList());
            palette.setItems(FXCollections.observableArrayList(cells));
        }
    }

    // Console

    private void initConsole() {
        groovyShell.setVariable("universe", controller.getUniverse());
        groovyShell.setVariable("palette", controller.getPalette());
    }

    private IReturnMessage execute(String commands) {
        IReturnMessage message = new ReturnMessage();
        try {
            message.appendInfo("Result: " + groovyShell.evaluate(commands)); //Eval.me(commands));
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
