package com.shopmart.pops.components.scenes;

import com.shopmart.pops.POPS;
import com.shopmart.pops.manager.data.entries.ItemAmount;
import com.shopmart.pops.manager.data.entries.Order;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Created by 0nepeop1e on 4/28/16.
 */
public class OrderScene extends Scene {
    public OrderScene(Order order){
        super(new HBox());
        HBox root = (HBox) getRoot();
        root.setSpacing(8);
        root.setPadding(new Insets(8, 8, 8, 8));
        root.setStyle("-fx-font-size:12pt;");
        root.setAlignment(Pos.CENTER);
        VBox v1 = new VBox(8);
        v1.setAlignment(Pos.CENTER);
        HBox h1 = new HBox(8);
        TableView<ItemAmount> items = new TableView<>();
        items.getItems().addAll(ItemAmount.fromMap(order.getItemsAmount()));
        TableColumn<ItemAmount, String> c1 = new TableColumn<>("Item");
        TableColumn<ItemAmount, String> c2 = new TableColumn<>("Barcode");
        TableColumn<ItemAmount, Integer> c3 = new TableColumn<>("Amount");
        TableColumn<ItemAmount, String> c4 = new TableColumn<>("Unit");
        TableColumn<ItemAmount, String> c5 = new TableColumn<>("Supplier");
        TableColumn<ItemAmount, String> c5_1 = new TableColumn<>("Name");
        TableColumn<ItemAmount, String> c5_2 = new TableColumn<>("Email");
        c1.setCellValueFactory(new PropertyValueFactory<>("name"));
        c2.setCellValueFactory(new PropertyValueFactory<>("barcode"));
        c3.setCellValueFactory(new PropertyValueFactory<>("amount"));
        c3.setOnEditCommit(e->items.getSelectionModel().getSelectedItem().setAmount(e.getNewValue()));
        c3.setEditable(true);
        c4.setCellValueFactory(new PropertyValueFactory<>("unit"));
        c5_1.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        c5_2.setCellValueFactory(new PropertyValueFactory<>("supplierEmail"));
        c5.getColumns().addAll(c5_1, c5_2);
        items.getColumns().addAll(c1,c2,c3,c4,c5);
        items.setEditable(true);
        h1.getChildren().add(items);
        v1.getChildren().add(h1);
        HBox h2 = new HBox();
        Pane space = new Pane();
        HBox.setHgrow(space, Priority.ALWAYS);
        Button back = new Button("Back");
        back.setOnAction(e-> POPS.getSceneManager().prevScene());
        h2.getChildren().addAll(back);
        v1.getChildren().add(h2);
        root.getChildren().add(v1);
    }
}
