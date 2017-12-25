package com.ui;

import com.Utilities;
import com.assets.AssetManager;
import com.components.Position;
import com.components.Sound;
import com.components.Sprite;
import com.core.*;
import groovy.lang.GroovyShell;
import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
//import javafx.scene.image.Image;
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
    @FXML ListView<String> paletteListView;
    @FXML ConsoleTextArea consoleTextArea;
    @FXML Canvas viewerCanvas;
    private GraphicsContext gc;

    private final IEditorController controller = new Controller(false);
    private final GroovyShell groovyShell = new GroovyShell();

    private IEntity selectedEntity;

    private final String IMAGE_ASSETS_PATH = "assets/images/";
    private final String AUDIO_ASSETS_PATH = "assets/audio/";

    @FXML
    public void initialize() {
        initPalette();
        initConsole();
        gc = viewerCanvas.getGraphicsContext2D();

        // Game loop
        AnimationTimer gameLoop = new AnimationTimer() {
            private long ago = 0L;

            @Override
            public void handle(long now) {
                // game logic
                controller.update(now - ago);

                // graphical rendering
                for(IEntity entity : controller.getUniverse().getEntities().stream()
                        .filter(e -> e.hasComponents(Sprite.class, Position.class))
                        .collect(Collectors.toSet())) {
                    ImageView imageView = entity.getComponent(Sprite.class).getImageView();
                    Position position = entity.getComponent(Position.class);
                    gc.drawImage(imageView.getImage(), position.getX(), position.getY());
                }

                ago = now;
            }
        };
        gameLoop.start();
    }

    // Palette

    private void initPalette() { // TODO: refactor and delegate to controller
        IEntitySystem palette = controller.getPalette();
        //String paletteEntitiesMapName = FieldUtils.getFieldsListWithAnnotation(palette.getClass(), ObservableCollection.class).get(0).getName();
        palette.addChangeListener(e -> {
            System.out.println("OLD: " + e.getOldValue());
            System.out.println("NEW: " + e.getNewValue());
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
                        if(name.contains("/")) { // TODO: figure out why we need this check for *nix
                            String[] path = name.split("/");
                            name = path[path.length-1];
                        }
                        imageView = new ImageView(Utilities.getResourceURL(IMAGE_ASSETS_PATH + name).toString());
                        imageView.setPreserveRatio(true);
                        imageView.setFitHeight(16.0); // TODO: refactor
                        setGraphic(imageView);
                        setText(name);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        });
        paletteListView.setOnMouseClicked(e -> {
//            System.out.println(paletteListView.getSelectionModel().getSelectedItem());
//            System.out.println(e.getX() + "\n" + e.getSceneX() + "\n" + e.getScreenX());
            String filename = paletteListView.getSelectionModel().getSelectedItem(); // TODO: avoid workaround
            selectedEntity = createEntityFromSelection(Utilities.getBaseFilename(filename));
        });

        // TODO: make cells of IEntity
        File[] files = (Utilities.getResourceFile(IMAGE_ASSETS_PATH)).listFiles();
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

    private void log(IReturnMessage message) {
        consoleTextArea.println();
//        consoleTextArea.println(message.getErrors());
//        consoleTextArea.println(message.getInfo());
        consoleTextArea.println(message.toString());
        consoleTextArea.println();
    }

    private void logError(String error) {
        consoleTextArea.println();
        // TODO: use color
        consoleTextArea.println("ERROR: " + error);
        consoleTextArea.println();
    }

    private void execute(String commands) {
        IReturnMessage message = new ReturnMessage();
        try {
            message.appendInfo("Result: " + groovyShell.evaluate(commands)); //Eval.me(commands));
        } catch (CompilationFailedException e) {
            message.appendErrors(e.getMessage());
        }
        log(message);
    }

    public void execute() {
        String commands = ""; // TODO: read user input
        execute(commands);
    }

    public void runScript() {
        IReturnMessage message = new ReturnMessage();
        FileChooser fileChooser = new FileChooser();
        Stage stage = new Stage();
        File file = fileChooser.showOpenDialog(stage);
        if(file!=null) {
            try {
                message.appendInfo("Read from " + file.getName() + ".");
                execute(Files.readFile(file));
            } catch (IOException e) {
                message.appendErrors(e.getMessage());
            }
        } else {
            message.appendErrors("No file selected.");
        }
        log(message);
    }

    public void saveScript() {
        String commands = consoleTextArea.getText(); // TODO: parse
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
        log(message);
    }

    // Canvas

    private IEntity createEntityFromSelection(String selectedAssetName) {
        if(selectedAssetName.equals("")) {
            logError("No sprite is selected.");
            return null;
        }
        IEntity entity = new Entity(selectedAssetName);
        if(!selectedAssetName.isEmpty()) {
            if (AssetManager.getImageView(selectedAssetName) != null) {
                log(entity.addComponents(new Sprite(selectedAssetName)));
            } else if (AssetManager.getAudioClip(selectedAssetName) != null) {
                log(entity.addComponents(new Sound(selectedAssetName)));
            } else {
                logError("Asset missing for the selected sprite.");
                return null;
            }
        }
        return entity;
    }

    public void hoverSelectedSprite(double x, double y) {
        if(selectedEntity!=null) {
            selectedEntity.addComponents(new Position(x, y, 0));
            controller.addSpritesToPalette(selectedEntity);
        }
    }

    public void placeSelectedSprite(double x, double y) {
        if(selectedEntity!=null) {
            log(controller.addSprite(selectedEntity, x, y));
        }
    }
}
