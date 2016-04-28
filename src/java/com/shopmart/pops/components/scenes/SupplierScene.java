package com.shopmart.pops.components.scenes;

import com.shopmart.pops.POPS;
import com.shopmart.pops.components.controls.entries.SupplierForm;
import com.shopmart.pops.manager.data.entries.Supplier;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Created by 0nepeop1e on 4/28/16.
 */
public class SupplierScene extends Scene {
    public SupplierScene(Supplier s){
        super(new HBox());
        HBox root = (HBox) getRoot();
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-font-size:12pt;");
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(16);
        SupplierForm sf = new SupplierForm(s);
        HBox hBox = new HBox();
        Button back = new Button("Back");
        back.setOnAction(e->POPS.getSceneManager().prevScene());
        Pane space = new Pane();
        HBox.setHgrow(space, Priority.ALWAYS);
        Button save = new Button("Save");
        save.setOnAction(e->{
            if (sf.apply()){
                if(sf.getSupplier().getId() == 0)
                    POPS.getDataManager().getSupplierManager()
                            .add(sf.getSupplier());
                POPS.getDataManager().saveTo(POPS.dataPath);
                ((SuppliersScene)POPS.getSceneManager().getPreviousScene()).refresh();
                POPS.getSceneManager().prevScene();
            }
        });
        hBox.getChildren().addAll(back, space, save);
        vBox.getChildren().addAll(sf, hBox);
        root.getChildren().add(vBox);
    }
}
