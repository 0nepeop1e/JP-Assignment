package com.shopmart.pops.components.abstracts;

import com.shopmart.pops.manager.data.abstracts.AbstractEntry;
import com.shopmart.pops.manager.data.abstracts.AbstractManager;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Created by 0nepeop1e on 4/28/16.
 */
public abstract class AbstractFinder<T extends AbstractEntry, E extends SearchFactory> extends VBox {

    protected TableView<T> dataView;
    protected ComboBox<E> searchBy;
    protected TextField keyword;

    public AbstractFinder(E[] options, E defVal){
        this.setSpacing(8);
        HBox hBox = new HBox();
        hBox.setSpacing(8);
        Label lbl = new Label("Search by:");
        HBox.setHgrow(lbl, Priority.NEVER);
        searchBy = new ComboBox<>();
        searchBy.setEditable(false);
        searchBy.getItems().addAll(options);
        searchBy.setValue(defVal);
        HBox.setHgrow(searchBy, Priority.NEVER);
        keyword = new TextField();
        HBox.setHgrow(keyword, Priority.ALWAYS);
        Button search = new Button("Search");
        HBox.setHgrow(search, Priority.NEVER);
        search.setOnAction(e->refresh());
        hBox.getChildren().addAll(lbl, searchBy, keyword, search);
        hBox.setAlignment(Pos.CENTER);
        VBox.setVgrow(hBox, Priority.NEVER);
        dataView = new TableView<>();
        dataView.setEditable(false);
        dataView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        dataView.getSelectionModel().setCellSelectionEnabled(false);
        dataView.getColumns().addAll(getColumns());
        VBox.setVgrow(dataView, Priority.ALWAYS);
        this.getChildren().addAll(hBox, dataView);
        refresh();
    }

    public void refresh(){
        dataView.getItems().removeAll(dataView.getItems());
        dataView.getItems().addAll(searchBy.getValue()
                .getFunction().call(getManager(), keyword.getText()));
    }

    public T getSelectedItem(){
        return dataView.getSelectionModel().getSelectedItem();
    }

    protected abstract TableColumn<T, ?>[] getColumns();
    protected abstract AbstractManager<T> getManager();
}
