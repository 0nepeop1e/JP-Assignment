package com.shopmart.pops.components.scenes;

import com.shopmart.pops.POPS;
import com.shopmart.pops.components.controls.IconLabel;
import com.shopmart.pops.manager.data.enums.AccessLevel;
import com.shopmart.pops.manager.resource.ResourceManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by 0nepeop1e on 4/26/16.
 */
public class MenuScene extends Scene {
    private Map<IconLabel, MenuItem> maps;
    public MenuScene(){
        super(new HBox());
        List<MenuItem> items = Arrays.stream(MenuItem.values())
                .filter(i->POPS.getDataManager()
                .getUserManager().getCurrentUser()
                .getAccessLevel().enoughFor(
                i.getAccessLevel())).collect(Collectors.toList());
        int nCol = (int) Math.ceil(Math.pow(items.size(), 0.5));
        maps = new HashMap<>();
        HBox root = (HBox) this.getRoot();
        VBox vbox = new VBox();
        root.setAlignment(Pos.CENTER);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(12);
        root.getChildren().add(vbox);
        Label l1 = new Label("Shopmart Sdn.Bhd.");
        l1.setStyle("-fx-font-size:26pt");
        Label l2 = new Label(String.format("Login as: %s",
                POPS.getDataManager().getUserManager()
                .getCurrentUser().getUsername()));
        l2.setStyle("-fx-font-size:16pt");
        Label l3 = new Label("");
        GridPane gp = new GridPane();
        gp.setAlignment(Pos.CENTER);
        for(int i = 0; i < items.size(); i++){
            MenuItem itm = items.get(i);
            IconLabel il = itm.createIconLabel();
            il.setOnMouseClicked(e->onItemClicked(itm));
            maps.put(il, itm);
            gp.add(il, i % nCol, i / nCol);
        }
        gp.setHgap(24);
        gp.setVgap(8);
        vbox.getChildren().addAll(l1, l2, l3, gp);
    }

    private void onItemClicked(MenuItem item){
        switch(item){

        }
    }

    private enum MenuItem{
        Users("img/users.png", "Users", AccessLevel.Manager),
        Items("img/items.png", "Items", AccessLevel.Staff),
        Suppliers("img/suppliers.png", "Suppliers", AccessLevel.Staff),
        Requests("img/requests.png", "Requisitions", AccessLevel.Staff),
        Orders("img/orders.png", "Orders", AccessLevel.Staff),
        Settings("img/account.png", "My Account", AccessLevel.Terminated),
        Logout("img/logout.png", "Logout", AccessLevel.NoAccess);
        private String image;
        private String label;
        @Getter
        private AccessLevel accessLevel;
        MenuItem(String image, String label, AccessLevel accessLevel){
            this.image = image;
            this.label = label;
            this.accessLevel = accessLevel;
        }

        public IconLabel createIconLabel(){
            return new IconLabel(ResourceManager.getImage(this.image), this.label);
        }
    }
}
