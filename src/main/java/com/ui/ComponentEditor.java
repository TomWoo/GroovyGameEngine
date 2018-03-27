package com.ui;

import com.Utilities;
import com.components.AbstractComponent;
import com.components.IComponent;
import com.core.IEntity;
import groovy.lang.GroovyClassLoader;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.reflections.Reflections;
//import org.reflections.Reflections;
//import sun.reflect.Reflection;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ComponentEditor extends Stage {
    private IEntity entity;
    private ScrollPane scrollPane = new ScrollPane();
    private GridPane gridPane = new GridPane();
    private HBox statusBar = new HBox();
    private Label statusLabel = new Label();
    private ComboBox<Class> addComponentComboBox = new ComboBox<>();
    private ComboBox<Class> removeComponentComboBox = new ComboBox<>();
    private HBox bottomBar = new HBox();

    // TODO: restrict window size
    public ComponentEditor(IEntity entity) {
        this.entity = entity;
        Group root = new Group();
        root.getChildren().add(scrollPane);
        //scrollPane.setContent(gridPane);
        //scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setPrefHeight(400); // TODO: redesign UI for responsiveness
        setScene(new Scene(root));

//        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        initStatusBar();
        initControlBar();
        refresh();

        setTitle("Component Editor");
        show();
    }

    @Deprecated
    private int getNumRows(GridPane gridPane) {
        int numRows = 0;
        try { // Access GridPane's private getter via reflection
            // Ref: https://stackoverflow.com/questions/520328/can-you-find-all-classes-in-a-package-using-reflection
            Method method = gridPane.getClass().getDeclaredMethod("getNumberOfRows");
            method.setAccessible(true);
            numRows = (int) method.invoke(gridPane);
        } catch (Exception ex) {
            System.out.println("GridPane.getNumberOfRows() does not exist.");
        }
        return numRows;
    }

    private void updateTables(List<IComponent> components) {
        gridPane.addRow(0, statusBar);

        int rowIdx = 1;
        for(IComponent component : components) {
            gridPane.addRow(rowIdx, generateTable(component));
            rowIdx++;
        }

        gridPane.addRow(rowIdx, bottomBar); // getNumRows(gridPane)
    }

    private void updateControlBar(List<IComponent> components) {
        // Ref: https://stackoverflow.com/questions/20766363/get-the-number-of-rows-in-a-javafx-gridpane
        Reflections reflections = new Reflections("com.components");
        List<Class> existingComponents = components.stream().map(IComponent::getClass).collect(Collectors.toList());
        List<Class> availableComponents = reflections.getSubTypesOf(AbstractComponent.class).stream()
                .filter(e -> !existingComponents.contains(e))
                .collect(Collectors.toList());
        addComponentComboBox.setItems(FXCollections.observableArrayList(availableComponents)); // TODO: display simple class names
//        choiceBox.setItems(FXCollections.observableArrayList(availableComponents));
        addComponentComboBox.getSelectionModel().selectFirst();
        removeComponentComboBox.setItems(FXCollections.observableArrayList(existingComponents));
        removeComponentComboBox.getSelectionModel().selectFirst();
    }

    private void refresh() {
        List<IComponent> components = new ArrayList<>(entity.getComponents());
        gridPane = new GridPane();
        scrollPane.setContent(gridPane); // TODO: new VBox(gridPane)?

        updateTables(components);
        updateControlBar(components);
    }

    private void initStatusBar() {
        //MenuBar statusBar = new MenuBar();
        statusLabel.setText("Double-click on any value to edit its contents. Press enter to commit.");
        statusLabel.setPrefHeight(32);
        statusBar.getChildren().add(statusLabel);
    }

    private void initControlBar() {
        Button addComponentButton = new Button("+ Component");
        addComponentButton.setOnMouseClicked(e -> {
            try {
                GroovyClassLoader loader = new GroovyClassLoader();
                IComponent c = (IComponent) loader.loadClass(
                        addComponentComboBox.getSelectionModel().getSelectedItem().getName()).newInstance();
                entity.addComponents(c);
                statusLabel.setText("Added " + c.toString());
                refresh();
            } catch (Exception ex) {
                statusLabel.setText("Fatal Exception: " + ex.getMessage());
            }
        });

        Button removeComponentButton = new Button("- Component");
        removeComponentButton.setOnMouseClicked(e -> {
            try {
                String componentName = removeComponentComboBox.getSelectionModel().getSelectedItem().getName();
                entity.removeComponents(Class.forName(componentName));
                statusLabel.setText("Removed " + componentName);
                refresh();
            } catch (Exception ex) {
                statusLabel.setText("Fatal Exception: " + ex.getMessage());
            }
        });

        bottomBar.getChildren().addAll(
                addComponentComboBox, addComponentButton,
                removeComponentComboBox, removeComponentButton);
    }

    // TODO: split into init() sections
    private TreeTableView<Map.Entry<String, Serializable>> generateTable(IComponent component) {
        TreeTableView<Map.Entry<String, Serializable>> table = new TreeTableView<>();
        TreeItem<Map.Entry<String, Serializable>> rootTreeItem = new TreeItem<>();
        table.setRoot(rootTreeItem);
        table.setShowRoot(false);
        table.setEditable(true);

        TreeTableColumn<Map.Entry<String, Serializable>, String> keyColumn = new TreeTableColumn<>(
                component.getClass().getSimpleName() + " Property");
        keyColumn.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getValue().getKey()));
        keyColumn.setPrefWidth(200);
        table.getColumns().add(keyColumn);

        TreeTableColumn<Map.Entry<String, Serializable>, String> valueColumn = new TreeTableColumn<>("Value");
        valueColumn.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getValue().getValue().toString()));
        valueColumn.setPrefWidth(360);
        table.getColumns().add(valueColumn);
        //valueColumn.setEditable(true);
        valueColumn.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        valueColumn.setOnEditCommit(e -> {
            String text = e.getNewValue();
            String key = e.getRowValue().getValue().getKey();
            Serializable value = Utilities.parseAsType(text, e.getRowValue().getValue().getValue().getClass());
            if(value!=null) {
                try {
                    component.setValue(key, value);
                    statusLabel.setText("Status: " + key + " = " + value);
                } catch (Exception ex) {
                    statusLabel.setText(ex.toString());
                }
            } else {
                statusLabel.setText("Error: entry incompatible with type");
            }
            e.getRowValue().setValue(new AbstractMap.SimpleEntry<>(key, component.getValue(key))); // need to refresh entry
            //table.refresh();
        });

        TreeTableColumn<Map.Entry<String, Serializable>, String> dataTypeColumn = new TreeTableColumn<>("Type");
        dataTypeColumn.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getValue().getValue().getClass().getSimpleName()));
        dataTypeColumn.setPrefWidth(200);
        table.getColumns().add(dataTypeColumn);

        for(String key : component.getKeys()) {
            Map.Entry<String, Serializable> entry = new AbstractMap.SimpleEntry<>(key, component.getValue(key));
            rootTreeItem.getChildren().add(new TreeItem<>(entry));
        }

        //table.setPrefSize(800, 600);
        table.setPrefHeight(200);
        return table;
    }
}
