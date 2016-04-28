package com.shopmart.pops.components.scenes;

import com.shopmart.pops.POPS;
import com.shopmart.pops.components.controls.finders.ItemFinder;
import com.shopmart.pops.manager.data.entries.Item;
import com.shopmart.pops.manager.data.entries.ItemAmount;
import com.shopmart.pops.manager.data.entries.Request;
import com.shopmart.pops.manager.data.enums.RequestStatus;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Created by 0nepeop1e on 4/28/16.
 */
public class RequestScene extends Scene {
    TableView<ItemAmount> items;
    Request request;
    public RequestScene(Request req){
        super(new HBox());
        request = req;
        HBox root = (HBox) getRoot();
        root.setSpacing(8);
        root.setPadding(new Insets(8, 8, 8, 8));
        root.setStyle("-fx-font-size:12pt;");
        root.setAlignment(Pos.CENTER);
        VBox v1 = new VBox(8);
        v1.setAlignment(Pos.CENTER);
        HBox h1 = new HBox(8);
        items = new TableView<>();
        items.getItems().addAll(ItemAmount.fromMap(req.getItemsAmount()));
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
        ItemFinder finder = new ItemFinder();
        VBox v2 = new VBox(8);
        v2.setAlignment(Pos.CENTER);
        Button add = new Button("<");
        TextField amount = new TextField("1");
        amount.setPrefWidth(48);
        add.setOnAction(e->{
            Item s = finder.getSelectedItem();
            if(s != null && !items.getItems().stream()
                    .filter(a->a.getItem() == s).findFirst().isPresent()){
                items.getItems().add(new ItemAmount(s, Integer.parseInt(amount.getText())));
            }
        });
        Button remove = new Button(">");
        remove.setOnAction(e->{
            ItemAmount s = items.getSelectionModel().getSelectedItem();
            if(s!=null) items.getItems().remove(s);
        });
        if(req.getStatus() != RequestStatus.Created){
            items.setEditable(false);
            add.setDisable(true);
            remove.setDisable(true);
            finder.setDisable(true);
        }
        v2.getChildren().addAll(amount, add, remove);
        h1.getChildren().addAll(v2, finder);
        v1.getChildren().add(h1);
        HBox h2 = new HBox();
        Pane space = new Pane();
        HBox.setHgrow(space, Priority.ALWAYS);
        Button back = new Button("Back");
        back.setOnAction(e-> POPS.getSceneManager().prevScene());
        Button save = new Button("Save");
        save.setOnAction(e->save());
        h2.getChildren().addAll(back, space, save);
        v1.getChildren().add(h2);
        root.getChildren().add(v1);
    }

    private void save() {
        if(items.getItems().size() == 0){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.initOwner(this.getWindow());
            a.setTitle("Request");
            a.setHeaderText("Nothing in the list!");
            return;
        }
        request.setModifier(POPS.getDataManager().getUserManager().getCurrentUser().getId());
        ItemAmount.toMap(items.getItems(), request.getItemsAmount());
        if(request.getId() == 0){
            request.setCreator(POPS.getDataManager().getUserManager().getCurrentUser().getId());
            POPS.getDataManager().getRequestManager().add(request);
        }
        POPS.getDataManager().saveTo(POPS.dataPath);
        ((RequestsScene)POPS.getSceneManager().getPreviousScene()).refresh();
        POPS.getSceneManager().prevScene();
    }
}
