package com.shopmart.pops.components.controls;

import com.shopmart.pops.POPS;
import com.shopmart.pops.manager.data.objects.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.Collection;

/**
 * Created by 0nepeop1e on 4/27/16.
 */
public class UserFinder extends VBox {
    private TableView<User> dataView;
    private TextField keyword;
    private ComboBox<SearchBy> searchBy;
    private Collection<User> cache;
    public UserFinder(){
        this.setSpacing(8);
        HBox hBox = new HBox();
        hBox.setSpacing(8);
        Label lbl = new Label("Search by:");
        HBox.setHgrow(lbl, Priority.NEVER);
        searchBy = new ComboBox<>();
        searchBy.getItems().addAll(SearchBy.values());
        searchBy.setValue(SearchBy.Username);
        searchBy.setEditable(false);
        HBox.setHgrow(searchBy, Priority.NEVER);
        keyword = new TextField();
        HBox.setHgrow(keyword, Priority.ALWAYS);
        Button search = new Button("Search");
        HBox.setHgrow(search, Priority.NEVER);
        search.setOnAction(e->refresh());
        hBox.getChildren().addAll(lbl, searchBy, keyword, search);
        VBox.setVgrow(hBox, Priority.NEVER);
        dataView = new TableView<>();
        dataView.setEditable(false);
        dataView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        dataView.getSelectionModel().setCellSelectionEnabled(false);
        TableColumn<User, String> c1 = new TableColumn<>("Username");
        TableColumn<User, String> c2 = new TableColumn<>("Staff ID");
        TableColumn<User, String> c3 = new TableColumn<>("Staff Name");
        TableColumn<User, String> c4 = new TableColumn<>("Access Level");
        c1.setCellValueFactory(cd->new SimpleStringProperty(
                cd.getValue().getUsername()));
        c2.setCellValueFactory(cd->new SimpleStringProperty(
                cd.getValue().getStaffId()));
        c3.setCellValueFactory(cd->new SimpleStringProperty(
                cd.getValue().getStaffName()));
        c4.setCellValueFactory(cd->new SimpleStringProperty(
                cd.getValue().getAccessLevel().toString()));
        dataView.getColumns().addAll(c1, c2, c3, c4);
        VBox.setVgrow(dataView, Priority.ALWAYS);
        this.getChildren().addAll(hBox, dataView);
        refresh();
    }

    private void refresh() {
        switch (searchBy.getValue()){
            case Username:
                cache = POPS.getDataManager().getUserManager()
                        .findByUsername(keyword.getText());
                break;
            case StaffId:
                cache = POPS.getDataManager().getUserManager()
                        .findByStaffId(keyword.getText());
                break;
            case StaffName:
                cache = POPS.getDataManager().getUserManager()
                        .findByStaffId(keyword.getText());
                break;
            case AccessLevel:
                cache = POPS.getDataManager().getUserManager()
                        .findByAccessLevel(keyword.getText());
                break;
        }
        dataView.getItems().clear();
        dataView.getItems().addAll(cache);
    }

    public User getSelectedUser(){
        return dataView.getSelectionModel().getSelectedItem();
    }

    private enum SearchBy{
        Username, StaffId, StaffName, AccessLevel;
        @Override
        public String toString(){
            if(this == StaffId) return "Staff ID";
            if(this == StaffName) return "Staff Name";
            if(this == AccessLevel) return "AccessLevel";
            return super.toString();
        }
    }
}
