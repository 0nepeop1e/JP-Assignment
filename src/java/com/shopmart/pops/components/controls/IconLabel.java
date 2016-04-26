package com.shopmart.pops.components.controls;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Created by 0nepeop1e on 4/26/16.
 */
public class IconLabel extends VBox {
    public IconLabel(Image image, String label){
        ImageView iv = new ImageView(image);
        Label lbl = new Label(label);
        HBox h1 = new HBox(), h2 = new HBox();
        h1.getChildren().add(iv);
        h2.getChildren().add(lbl);
        h1.setAlignment(Pos.CENTER);
        h2.setAlignment(Pos.CENTER);
        this.getChildren().addAll(h1, h2);
        this.setSpacing(4);
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(4, 4, 4, 4));
    }
}
