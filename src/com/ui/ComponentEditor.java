package com.ui;

import com.Utilities;
import com.components.IComponent;
import com.core.IEntity;
import groovy.ui.view.BasicStatusBar;
import groovy.util.MapEntry;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ComponentEditor extends Stage {
    public ComponentEditor(IEntity entity) {
        List<IComponent> components = new ArrayList<>(entity.getComponents());
        Group root = new Group();
        ScrollPane scrollPane = new ScrollPane();
        root.getChildren().add(scrollPane);
        GridPane gridPane = new GridPane();
        scrollPane.setContent(gridPane); // TODO: new VBox(gridPane)?
        //scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setPrefHeight(400);
        setScene(new Scene(root));
//        BorderPane borderPane = new BorderPane();
//        borderPane.setCenter(generateTable(components));
//        root.getChildren().add(borderPane);
        HBox statusBar = new HBox();
        //statusBar.setStyle("-fx-background: #444444;"); // TODO: check
        //statusBar.setPrefHeight(40);
        //MenuBar statusBar = new MenuBar();
        Label label = new Label("Double-click on any value to edit its contents. Press enter to commit.");
        label.setPrefHeight(32);
        statusBar.getChildren().add(label);
        gridPane.addRow(0, label);
        int i = 1;
        for(IComponent component : components) {
            gridPane.addRow(i, generateTable(component, label));
            i++;
        }
        setTitle("Component Editor");
        show();
    }

    // TODO: split into init() sections
    private TreeTableView<Map.Entry<String, Serializable>> generateTable(IComponent component, Label label) {
        TreeTableView<Map.Entry<String, Serializable>> table = new TreeTableView<>();
        TreeItem<Map.Entry<String, Serializable>> rootTreeItem = new TreeItem<>();
        table.setRoot(rootTreeItem);
        table.setShowRoot(false);
        table.setEditable(true);

        TreeTableColumn<Map.Entry<String, Serializable>, String> keyColumn = new TreeTableColumn(
                component.getClass().getSimpleName() + " Property");
        keyColumn.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getValue().getKey()));
        keyColumn.setPrefWidth(200);
        table.getColumns().add(keyColumn);

        TreeTableColumn<Map.Entry<String, Serializable>, String> valueColumn = new TreeTableColumn("Value");
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
                    label.setText("Status: " + key + " = " + value);
                } catch (Exception ex) {
                    //System.out.println(ex.toString());
                    label.setText(ex.toString());
                }
            } else {
                label.setText("Error: entry incompatible with type");
            }
            e.getRowValue().setValue(new AbstractMap.SimpleEntry<>(key, component.getValue(key))); // need to refresh entry
            //table.refresh();
        });

        TreeTableColumn<Map.Entry<String, Serializable>, String> dataTypeColumn = new TreeTableColumn("Type");
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
