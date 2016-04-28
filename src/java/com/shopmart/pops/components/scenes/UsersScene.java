package com.shopmart.pops.components.scenes;

import com.shopmart.pops.POPS;
import com.shopmart.pops.components.controls.finders.UserFinder;
import com.shopmart.pops.manager.data.enums.AccessLevel;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.Optional;

/**
 * Created by 0nepeop1e on 4/27/16.
 */
public class UsersScene extends Scene {
    private UserFinder uf;
    public UsersScene(){
        super(new VBox());
        VBox root = (VBox) this.getRoot();
        root.setSpacing(8);
        root.setPadding(new Insets(8, 8, 8, 8));
        root.setStyle("-fx-font-size:12pt;");
        uf = new UserFinder();
        VBox.setVgrow(uf, Priority.ALWAYS);
        root.getChildren().add(uf);
        HBox hBox = new HBox();
        hBox.setSpacing(8);
        Button back = new Button("Back");
        back.setOnAction(e-> POPS.getSceneManager().prevScene());
        HBox space = new HBox();
        HBox.setHgrow(space, Priority.ALWAYS);
        Button del = new Button("Delete");
        del.setOnAction(e->delete());
        Button edit = new Button("Edit");
        edit.setOnAction(e->POPS.getSceneManager().nextScene(
                new UserEditScene(uf.getSelectedItem())));
        Button add = new Button("New User");
        add.setOnAction(e->POPS.getSceneManager().nextScene(new CreateUserScene()));
        hBox.getChildren().addAll(back, space);
        if(POPS.getDataManager().getUserManager().getCurrentUser()
                .getAccessLevel().enoughFor(AccessLevel.Administrator))
            hBox.getChildren().add(del);
        hBox.getChildren().addAll(edit, add);
        VBox.setVgrow(hBox, Priority.NEVER);
        root.getChildren().add(hBox);
    }

    public void refresh(){
        uf.refresh();
    }

    private void delete() {
        if(uf.getSelectedItem() != null){
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.initOwner(this.getWindow());
            if(uf.getSelectedItem() == POPS.getDataManager()
                    .getUserManager().getCurrentUser()){
                a.setAlertType(Alert.AlertType.ERROR);
                a.setTitle("Delete");
                a.setHeaderText("Failed to Delete!");
                a.setContentText("You cannot delete your own account.");
                a.showAndWait();
                return;
            }
            a.setTitle("Delete");
            a.setHeaderText("Are you sure to delete this entry?");
            Optional<ButtonType> obt = a.showAndWait();
            obt.ifPresent(b->{
                if(b==ButtonType.OK){
                    POPS.getDataManager().getUserManager()
                        .removeById(uf.getSelectedItem().getId());
                    POPS.getDataManager().saveTo(POPS.dataPath);
                    uf.refresh();
                }
            });
        }
    }
}
