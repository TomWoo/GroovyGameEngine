package com.ui;

import com.components.AbstractComponent;
import com.components.IComponent;
import com.core.IEntity;
import com.core.IObservable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ComponentEditor extends Stage {
    public ComponentEditor(IEntity entity) {
        List<IComponent> components = new ArrayList<>(entity.getComponents());
        generateTable(components);
    }

    private TableView<IComponent> generateTable(List<IComponent> components) {
        TableView<IComponent> table = new TableView<>();
        table.setItems(FXCollections.observableArrayList(components));//.stream()
//                .map(e -> e.getClass().getName())
//                .collect(Collectors.toList())));

        TableColumn nameCol = new TableColumn("Component");
        nameCol.setCellValueFactory(e -> new SimpleStringProperty(e.getClass().getName()));

        TableColumn valueCol = new TableColumn("Value");
        nameCol.setCellValueFactory(e -> new SimpleStringProperty(e.toString())); // TODO!

        return table;
    }
}
