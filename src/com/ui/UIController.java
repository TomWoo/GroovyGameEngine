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
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
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
    @FXML ScrollPane viewPane;
    private Group root = new Group();
    //@FXML Canvas canvas;
    //private GraphicsContext gc;

    private final IEditorController controller = new Controller(false);
    private final GroovyShell groovyShell = new GroovyShell();

    private IEntity selectedEntity;

    private void renderGraphics(IEntitySystem entitySystem, double alpha) {
        //gc.setGlobalAlpha(alpha);
        for(IEntity entity : entitySystem.getEntities().stream()
                .filter(e -> e.hasComponents(Sprite.class, Position.class))
                .collect(Collectors.toList())) {
            ImageView imageView = entity.getComponent(Sprite.class).getImageView();
            Position position = entity.getComponent(Position.class);
            //gc.drawImage(imageView.getImage(), position.getX(), position.getY());
            ImageView newImageView = new ImageView(imageView.getImage());
            newImageView.setOpacity(alpha);
            newImageView.setX(position.getX());
            newImageView.setY(position.getY());
            root.getChildren().add(newImageView);
//            newImageView.toFront();
        }
    }

    @FXML
    public void initialize() {
        initPalette();
        initConsole();
        initCanvas();

        // Game loop
        AnimationTimer gameLoop = new AnimationTimer() {
            private long ago = 0L;

            @Override
            public void handle(long now) {
                // game logic
                controller.update(now - ago);

                // graphical rendering
                //gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                root.getChildren().clear();
                renderGraphics(controller.getUniverse(), 1.0);
                renderGraphics(controller.getPalette() , 0.5);

                ago = now;
            }
        };
        gameLoop.start();
    }

    // Palette

    private void initPalette() { // TODO: refactor and delegate to controller
        //IEntitySystem palette = controller.getPalette();
        //String paletteEntitiesMapName = FieldUtils.getFieldsListWithAnnotation(palette.getClass(), ObservableCollection.class).get(0).getName();

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
                        imageView = new ImageView(Utilities.getResourceFilename(AssetManager.IMAGE_ASSETS_PATH + name));
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
            String filename = paletteListView.getSelectionModel().getSelectedItem(); // TODO: avoid workaround
            selectedEntity = createEntityFromSelection(Utilities.getBaseFilename(filename));
            controller.getPalette().clear();
            hoverSelectedSprites(selectedEntity, e.getX(), e.getY());
        });

        // TODO: make cells of IEntity
        File[] files = (Utilities.getResourceFile(AssetManager.IMAGE_ASSETS_PATH)).listFiles();
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
        if(!controller.isPlaying()) { // TODO: find a better solution to prevent incessant printing
//        consoleTextArea.println(message.getErrors());
//        consoleTextArea.println(message.getInfo());
            consoleTextArea.println(message.toString());
        }
    }

    private void logError(String error) {
        if(!controller.isPlaying()) {
            consoleTextArea.println("ERROR: " + error); // TODO: use color
        }
    }

    private void execute(String commands) {
        IReturnMessage message = new ReturnMessage();
        try {
            message.appendInfo("Result: " + groovyShell.evaluate(commands)); //Eval.me(commands));
        } catch (CompilationFailedException e) {
            message.appendError(e.getMessage());
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
                message.appendError(e.getMessage());
            }
        } else {
            message.appendError("No file selected.");
        }
        log(message);
    }

    public void saveScript() {
        String commands = consoleTextArea.getText(); // TODO: parse, error-check
        IReturnMessage message = new ReturnMessage();
        FileChooser fileChooser = new FileChooser();
        Stage stage = new Stage();
        File file = fileChooser.showSaveDialog(stage);
        if(file!=null) {
            try {
                Files.writeFile(commands, file);
                message.appendInfo("Written to " + file.getName() + ".");
            } catch (IOException e) {
                message.appendError(e.getMessage());
            }
        } else {
            message.appendError("No file selected.");
        }
        log(message);
    }

    // Canvas

    private void initCanvas() {
        //gc = canvas.getGraphicsContext2D();
        viewPane.setContent(root);
        //canvas.setOnMouseMoved(e -> {
        viewPane.setOnMouseMoved(e -> {
            if(selectedEntity!=null) {
                if(!selectedEntity.hasComponents(Position.class)) {
                    selectedEntity.addComponents(new Position(e.getX(), e.getY(), 0));
                }
                selectedEntity.getComponent(Position.class).setXY(e.getX(), e.getY());
            }
        });
        //canvas.setOnMouseClicked(e -> {
        viewPane.setOnMouseClicked(e -> {
            if(e.getButton()== MouseButton.PRIMARY) {
                boolean result = placeSelectedSprites(selectedEntity, e.getX(), e.getY());
                if(!result) {
                    // TODO: select sprite on canvas
                }
            } else { // de-select
                //consoleTextArea.println(String.valueOf(selectedEntity==null));
                selectedEntity = null;
                controller.getPalette().clear();
            }
        });
    }

    @Deprecated // TODO: replace with IEntity cells
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

    // TODO: handle multiple selections
    private boolean hoverSelectedSprites(IEntity selectedEntity, double x, double y) {
        if(selectedEntity!=null) {
            selectedEntity.addComponents(new Position(x, y, 0));
            log(controller.addSpritesToPalette(selectedEntity));
            return true;
        }
        return false;
    }

    private boolean placeSelectedSprites(IEntity selectedEntity, double x, double y) {
        if(selectedEntity!=null) {
            IEntity entity = Utilities.deepClone(selectedEntity);
            log(controller.addSprite(entity, x, y));
            //selectedEntity.getComponent(Sprite.class).getImageView().toFront();
            //controller.getUniverse().toTop(entity.getUID());
            return true;
        }
        return false;
    }

    // Entity editor

    private boolean selectSpriteOnCanvas(IEntity entity) {
        if(entity!=null) {
            // TODO
        }
        return false;
    }
}
