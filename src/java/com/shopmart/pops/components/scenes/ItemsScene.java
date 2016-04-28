package com.shopmart.pops.components.scenes;

import com.shopmart.pops.POPS;
import com.shopmart.pops.components.controls.finders.ItemFinder;
import com.shopmart.pops.components.controls.finders.SupplierFinder;
import com.shopmart.pops.manager.data.entries.Item;
import com.shopmart.pops.manager.data.entries.Supplier;
import com.shopmart.pops.manager.data.enums.AccessLevel;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Created by 0nepeop1e on 4/28/16.
 */
public class ItemsScene extends Scene {
    private ItemFinder finder;
    public ItemsScene(){
        super(new VBox());
        VBox root = (VBox) getRoot();
        root.setSpacing(8);
        root.setPadding(new Insets(8, 8, 8, 8));
        root.setStyle("-fx-font-size:12pt;");
        finder = new ItemFinder();
        VBox.setVgrow(finder, Priority.ALWAYS);
        HBox hBox = new HBox();
        hBox.setSpacing(8);
        Button back = new Button("Back");
        back.setOnAction(e-> POPS.getSceneManager().prevScene());
        HBox.setHgrow(back, Priority.NEVER);
        Pane space = new Pane();
        HBox.setHgrow(space, Priority.ALWAYS);
        Button del = new Button("Delete");
        del.setOnAction(e->{
            if(finder.getSelectedItem() != null){
                POPS.getDataManager().getItemManager()
                        .removeById(finder.getSelectedItem().getId());
                POPS.getDataManager().saveTo(POPS.dataPath);
            }
        });
        HBox.setHgrow(del, Priority.NEVER);
        Button edit = new Button("Edit");
        edit.setOnAction(e->{
            if(finder.getSelectedItem()!=null)
                POPS.getSceneManager().nextScene(new ItemScene(
                        finder.getSelectedItem()));
        });
        HBox.setHgrow(edit, Priority.NEVER);
        Button add = new Button("Add New");
        add.setOnAction(e->POPS.getSceneManager().nextScene(
                new ItemScene(new Item())));
        HBox.setHgrow(add, Priority.NEVER);
        hBox.getChildren().addAll(back, space);
        AccessLevel lvl = POPS.getDataManager().getUserManager()
                .getCurrentUser().getAccessLevel();
        if(lvl.enoughFor(AccessLevel.Administrator))
            hBox.getChildren().add(del);
        if(lvl.enoughFor(AccessLevel.Manager))
            hBox.getChildren().add(edit);
        hBox.getChildren().add(add);
        root.getChildren().addAll(finder, hBox);
    }

    public void refresh() {
        finder.refresh();
    }
}
