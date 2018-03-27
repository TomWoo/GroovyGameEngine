package com.ui;

import com.Utilities;
import com.core.AssetManager;
import com.components.Position;
import com.components.Sound;
import com.components.Sprite;
import com.core.*;
import groovy.lang.GroovyShell;
import javafx.animation.AnimationTimer;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.testng.reporters.Files;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UIController {
    @FXML ListView<String> assetPaletteListView;
    //@FXML ZoomableScrollPane viewPane;
    @FXML ScrollPane viewPane;
    @FXML ToggleButton playButton;
    @FXML TreeTableView<IEntity> universeEntityTable;
    @FXML TreeTableColumn<IEntity, String> entityNameColumn;
    @FXML TreeTableColumn<IEntity, String> entityUniqueIDColumn;
    @FXML TreeTableColumn<IEntity, String> entityGroupsColumn;
    @FXML ConsoleTextArea consoleTextArea;
    //private ConsoleTextArea consoleTextArea = new ConsoleTextArea();

    //@FXML
    //Canvas canvas;
    //private GraphicsContext gc;
    private final Group root = new Group();

    private final IEditorController controller = new Controller(false);
    private final GroovyShell groovyShell = new GroovyShell();

    private IEntity selectedEntity;

    private double scrollSensitivity = 8.0;

    private void renderGraphics(IEntitySystem entitySystem, double alpha) {
        //gc.setGlobalAlpha(alpha);
        for(IEntity entity : entitySystem.getEntitiesWithComponents(Sprite.class, Position.class)) {
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
        initUniverseEntityTable();

        // Top-level controls
        playButton.setOnMouseClicked(e -> {
            if(e.getButton()==MouseButton.PRIMARY) {
                if(controller.togglePlayPause()) {
                    playButton.setText("Playing");
                    playButton.setSelected(true);
                } else {
                    playButton.setText("Play");
                    playButton.setSelected(false);
                }
            }
        });

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

        assetPaletteListView.setCellFactory(listCell -> new ListCell<String>() {
            @Override
            public void updateItem(String name, boolean empty) {
            super.updateItem(name, empty);
            if (empty) {
                setGraphic(null);
                setText(null);
            } else {
                ImageView imageView;
                try {
                    if(name.contains("/")) { // TODO: support *nix
                        String[] path = name.split("/");
                        name = path[path.length-1];
                    }
                    imageView = new ImageView(Utilities.getResourceFilename(AssetManager.IMAGE_ASSETS_PATH + name));
                    imageView.setPreserveRatio(true);
                    imageView.setFitHeight(16.0); // TODO: refactor out
                    setGraphic(imageView);
                    setText(name);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            }
        });
        assetPaletteListView.setOnMouseClicked(e -> {
            String filename = assetPaletteListView.getSelectionModel().getSelectedItem(); // TODO: avoid workaround
            selectedEntity = createEntityFromSelection(Utilities.getBaseFilename(filename));
            controller.getPalette().clear();
            hoverSelectedSprites(selectedEntity, e.getX(), e.getY());
        });

        // TODO: make cells of IEntity
        File[] files = (Utilities.getResourceFile(AssetManager.IMAGE_ASSETS_PATH)).listFiles();
        if(files != null) {
            List<String> cells = Arrays.stream(files).map(e -> {
                String[] filePathArr = e.getAbsolutePath().split("\\\\");
                return filePathArr[filePathArr.length-1];
            }).collect(Collectors.toList());
            assetPaletteListView.setItems(FXCollections.observableArrayList(cells));
        }
    }

    // Universe entity table

    private void initUniverseEntityTable() {
        TreeItem<IEntity> rootTreeItem = new TreeItem<>();
        universeEntityTable.setRoot(rootTreeItem);
        universeEntityTable.setShowRoot(false);
        universeEntityTable.setEditable(true); // TODO: implement
//        universeEntityTable.setRowFactory(
//                (TreeTableColumn.CellDataFeatures<IEntity, String> param) -> new ReadOnlyStringWrapper(param.getValue().getValue().getName())
//        );
//        universeEntityTable.getColumns().get(0).setCellValueFactory(
        entityNameColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<IEntity, String> e) -> new ReadOnlyStringWrapper(e.getValue().getValue().getName())
        );
        entityUniqueIDColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<IEntity, String> e) -> new ReadOnlyStringWrapper(e.getValue().getValue().getUID())
        );
        entityGroupsColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<IEntity, String> e) -> new ReadOnlyStringWrapper(
                        String.join(",", e.getValue().getValue().getGroupIDs())
                )
        );
        controller.getUniverse().addChangeListener(p -> {
            //rootTreeItem.getChildren().clear();
            //rootTreeItem.getChildren().addAll(controller.getUniverse().getEntities().stream().map(e -> new TreeItem<>(e)).collect(Collectors.toList()));
            if(p.getNewValue().getClass().equals(Entity.class)) {
                rootTreeItem.getChildren().add(new TreeItem<>((IEntity) p.getNewValue()));
            } // TODO: handle removal
//            switch(p.getPropertyName()) { // TODO: get type
//                case "ADDED":
//                    rootTreeItem.getChildren().add(new TreeItem<>((IEntity) p.getNewValue()));
//                case "REMOVED":
//                    rootTreeItem.getChildren().remove(new TreeItem<>((IEntity) p.getOldValue()));
//                default:
//                    break;
//            }
        });
        universeEntityTable.setRowFactory(p -> {
            // Reference: https://stackoverflow.com/questions/26563390/detect-doubleclick-on-row-of-tableview-javafx
            TreeTableRow<IEntity> row = new TreeTableRow<>();
            row.setOnMouseClicked(event -> {
                if(event.getClickCount()==2 && (!row.isEmpty())) {
                    IEntity entity = row.getItem();
                    ComponentEditor editor = new ComponentEditor(entity);
                    editor.show();
                }
            });
            return row;
        });
    }

    // Console

    private void initConsole() {
        groovyShell.setVariable("universe", controller.getUniverse());
        //groovyShell.setProperty("universe", controller.getUniverse());
        groovyShell.setVariable("palette" , controller.getPalette());

        // REPL behavior
        consoleTextArea.setOnKeyPressed(e -> {
            if(e.getCode()== KeyCode.ENTER) {
                e.consume();
                execute();
            }
        });
    }

    private void log(IReturnMessage message) {
        if(!controller.isPlaying()) { // TODO: prevent incessant printing to console
            consoleTextArea.println(message.toString());
        }
    }

    private void logError(String error) {
        if(!controller.isPlaying()) {
            consoleTextArea.println("ERROR: " + error); // TODO: color text red
        }
    }

    private void execute(String commands) {
        IReturnMessage message = new ReturnMessage();
        try {
            message.appendInfo("Result: " + groovyShell.evaluate(commands));
            //message.appendInfo("Result: " + Eval.me(commands));
        } catch (Exception e) {
            message.appendError(e.getMessage());
        }
        log(message);
    }

    public void execute() {
        String commands = consoleTextArea.getLastLine();
        consoleTextArea.println();
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
        viewPane.setContent(new VBox(root)); // VBox required for scrolling
        viewPane.setPannable(true);
        //viewPane.setTarget(root);
//        viewPane.setFitToWidth(true);
//        viewPane.setFitToHeight(true);
        // TODO: implement real scrolling
        viewPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        viewPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        // Hotkey support
        //canvas.setOnMouseMoved(e -> {
        viewPane.setOnMouseMoved(e -> {
            if(selectedEntity!=null) {
                if(!selectedEntity.hasComponents(Position.class)) {
                    selectedEntity.addComponents(new Position());
                }
                selectedEntity.getComponent(Position.class).setXY(
                        e.getX(),
                        e.getY()
                ); // TODO: account for zoom
            }
        });
        //canvas.setOnMouseClicked(e -> {
        viewPane.setOnMouseClicked(e -> {
            if(e.getButton() == MouseButton.PRIMARY) {
                placeSelectedSprites(selectedEntity, e.getX(), e.getY());
                // TODO: select if selectedEntity==null
            }
        });
        viewPane.setOnKeyPressed(e -> {
            if(e.getCode()==KeyCode.ESCAPE) { // de-select
                selectedEntity = null;
                controller.getPalette().clear();
            }
        });
        viewPane.addEventFilter(ScrollEvent.ANY, e -> { // TODO: setOnScroll buggy?
            if(!e.isShiftDown()) {
                //e.consume();
                double scrollDelta = e.getDeltaY() / 40.0; // TODO: test on other machines
                double zoomFactor = Math.pow(2.0, scrollDelta/scrollSensitivity);
                root.setScaleX(root.getScaleX() * zoomFactor);
                root.setScaleY(root.getScaleY() * zoomFactor);
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

    private void placeSelectedSprites(IEntity selectedEntity, double x, double y) {
        if(selectedEntity!=null) {
            IEntity entity = Utilities.deepClone(selectedEntity);
            log(controller.addSprite(entity, x, y));
            //selectedEntity.getComponent(Sprite.class).getImageView().toFront();
            //controller.getUniverse().sendEntityToTop(entity.getName());
        }
    }

    // Entity editor

    private boolean selectSpriteOnCanvas(IEntity entity) {
        if(entity!=null) {
            selectedEntity = entity; // TODO: test
            return true;
        }
        return false;
    }
}
