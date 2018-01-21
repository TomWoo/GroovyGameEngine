package com.ui;

import com.collections.SerializableObservableMap;
import com.components.AbstractComponent;
import com.components.IComponent;
import com.core.IEntity;
import com.core.IObservable;
import groovy.util.MapEntry;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.apache.commons.lang3.tuple.Pair;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EntityComponentEditor extends Stage {
    public EntityComponentEditor(IEntity entity) {
        List<IComponent> components = new ArrayList<>(entity.getComponents());
        Group root = new Group();
        setScene(new Scene(root));
//        BorderPane borderPane = new BorderPane();
//        borderPane.setCenter(generateTable(components));
//        root.getChildren().add(borderPane);
        for(IComponent component : components) {
            root.getChildren().add(generateTable(component));
        }
        setTitle("Entity-Component Editor");
        show();
    }

    private TreeTableView<Map.Entry<String, Serializable>> generateTable(IComponent component) {
        TreeTableView<Map.Entry<String, Serializable>> table = new TreeTableView<>();

//        TreeTableColumn nameColumn = new TreeTableColumn("Component");
//        nameColumn.setCellValueFactory(e -> new SimpleStringProperty(e.getClass().getName()));
//        nameColumn.setPrefWidth(200);
//        table.getColumns().add(nameColumn);

        TreeItem<Map.Entry<String, Serializable>> rootTreeItem = new TreeItem<>();
        table.setRoot(rootTreeItem);
        table.setShowRoot(false);
        //rootTreeItem.getChildren().addAll(components.stream().map(e -> new TreeItem<>(e)).collect(Collectors.toList()));
//        for(IComponent component : components) {
//            rootTreeItem.getChildren().add(new TreeItem<>(component));
//        }

        TreeTableColumn<Map.Entry<String, Serializable>, String> keyColumn = new TreeTableColumn("Property");
        keyColumn.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getValue().getValue().toString()));
        keyColumn.setPrefWidth(200);
        table.getColumns().add(keyColumn);

        TreeTableColumn<Map.Entry<String, Serializable>, String> valueColumn = new TreeTableColumn("Value");
        valueColumn.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getValue().getValue().toString()));
        valueColumn.setPrefWidth(400);
        table.getColumns().add(valueColumn);

//        tablesetItems.setItems(FXCollections.observableArrayList(components));//.stream()
//                .map(e -> e.getClass().getName())
//                .collect(Collectors.toList())));
        for(String key : component.getKeys()) {
            Map.Entry<String, Serializable> entry = new AbstractMap.SimpleEntry<>(key, component.getValue(key));
            rootTreeItem.getChildren().add(new TreeItem<>(entry));
        }
        table.setPrefSize(800, 600); // TODO: extract

        return table;
    }
}
