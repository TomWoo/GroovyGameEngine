package com.ui;

import com.components.IComponent;
import com.core.IEntity;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
        scrollPane.setContent(new VBox(gridPane));
        //scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setPrefHeight(600);
        setScene(new Scene(root));
//        BorderPane borderPane = new BorderPane();
//        borderPane.setCenter(generateTable(components));
//        root.getChildren().add(borderPane);
        int i = 0;
        for(IComponent component : components) {
            gridPane.addRow(i, generateTable(component));
            i++;
        }
        setTitle("Component Editor");
        show();
    }

    private TreeTableView<Map.Entry<String, Serializable>> generateTable(IComponent component) {
        TreeTableView<Map.Entry<String, Serializable>> table = new TreeTableView<>();
        TreeItem<Map.Entry<String, Serializable>> rootTreeItem = new TreeItem<>();
        table.setRoot(rootTreeItem);
        table.setShowRoot(false);

        TreeTableColumn<Map.Entry<String, Serializable>, String> keyColumn = new TreeTableColumn(
                component.getClass().getSimpleName() + " Property");
        keyColumn.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getValue().getKey()));
        keyColumn.setPrefWidth(200);
        table.getColumns().add(keyColumn);

        TreeTableColumn<Map.Entry<String, Serializable>, String> valueColumn = new TreeTableColumn("Value");
        valueColumn.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getValue().getValue().toString()));
        valueColumn.setPrefWidth(400);
        table.getColumns().add(valueColumn);

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
