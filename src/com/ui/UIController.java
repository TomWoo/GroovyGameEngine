package com.ui;

import com.core.*;
import com.UtilityFunctions;
import groovy.lang.GroovyShell;
import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.codehaus.groovy.control.CompilationFailedException;
import org.testng.reporters.Files;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UIController {
    @FXML ListView<String> paletteListView;
    @FXML ConsoleTextArea consoleTextArea;

    private IEditorController controller = new Controller(false);
    private GroovyShell groovyShell = new GroovyShell();

    @FXML
    public void initialize() {
        initPalette();
        initConsole();

        // Game loop
        AnimationTimer gameLoop = new AnimationTimer() {
            private long ago = 0L;

            @Override
            public void handle(long now) {
                controller.update(now - ago);
                ago = now;
            }
        };
        gameLoop.start();
    }

    // Palette

    private void initPalette() { // TODO: refactor and derive from controller
        IEntitySystem palette = controller.getPalette();
        // TODO: palette.addPropertyChangeListener(palette.getObservablePropertyNames().get(0), e -> {});
        String paletteEntitiesMapName = FieldUtils.getFieldsListWithAnnotation(palette.getClass(), ObservableProperty.class).get(0).getName();
        palette.addPropertyChangeListener(paletteEntitiesMapName, e -> {
            System.out.println("OLD: " + e.getOldValue());
            System.out.println("OLD: " + e.getNewValue());
        });
        paletteListView.setCellFactory(listCell -> new ListCell<String>() {
            @Override
            public void updateItem(String name, boolean empty) {
                super.updateItem(name, empty);
                if (empty) {
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
            paletteListView.setItems(FXCollections.observableArrayList(cells));
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
        String commands = consoleTextArea.getText(); // TODO
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
