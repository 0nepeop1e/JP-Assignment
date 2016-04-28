package com.shopmart.pops.components.scenes;

import com.shopmart.pops.POPS;
import com.shopmart.pops.components.controls.finders.OrderFinder;
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
public class OrdersScene extends Scene {
    OrderFinder rf;
    public OrdersScene(){
        super(new VBox());
        AccessLevel lvl = POPS.getDataManager().getUserManager()
                .getCurrentUser().getAccessLevel();
        VBox root = (VBox) getRoot();
        root.setSpacing(8);
        root.setPadding(new Insets(8, 8, 8, 8));
        root.setStyle("-fx-font-size:12pt;");
        rf = new OrderFinder();
        VBox.setVgrow(rf, Priority.ALWAYS);
        root.getChildren().add(rf);
        HBox hBox = new HBox();
        hBox.setSpacing(8);
        Button back = new Button("Back");
        back.setOnAction(e->POPS.getSceneManager().prevScene());
        hBox.getChildren().add(back);
        Pane space = new Pane();
        HBox.setHgrow(space, Priority.ALWAYS);
        hBox.getChildren().add(space);
        if(lvl.enoughFor(AccessLevel.Manager)){
            Button del = new Button("Delete");
            del.setOnAction(e->{
                if(rf.getSelectedItem() != null){
                    POPS.getDataManager().getRequestManager()
                            .removeById(rf.getSelectedItem().getId());
                    POPS.getDataManager().saveTo(POPS.dataPath);
                    rf.refresh();
                }
            });
            hBox.getChildren().add(del);
        }
        Button edit = new Button("View");
        edit.setOnAction(e->{
            if(rf.getSelectedItem() != null)
                POPS.getSceneManager().nextScene(
                        new OrderScene(rf.getSelectedItem()));
        });
        hBox.getChildren().add(edit);
        root.getChildren().add(hBox);
    }
}
