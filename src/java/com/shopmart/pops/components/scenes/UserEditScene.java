package com.shopmart.pops.components.scenes;

import com.shopmart.pops.POPS;
import com.shopmart.pops.components.controls.entries.UserForm;
import com.shopmart.pops.manager.data.entries.User;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Created by 0nepeop1e on 4/27/16.
 */
public class UserEditScene extends Scene {

    private UserForm userForm;

    public UserEditScene(User u){
        super(new HBox());
        HBox root = (HBox) this.getRoot();
        root.setStyle("-fx-font-size:12pt;");
        root.setAlignment(Pos.CENTER);
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(16);
        userForm = new UserForm(u);
        vBox.getChildren().add(userForm);
        HBox hBox = new HBox();
        hBox.setSpacing(8);
        Button btn1 = new Button("Back");
        btn1.setOnAction(e-> POPS.getSceneManager().prevScene());
        Pane space = new Pane();
        Button btn2 = new Button("Save");
        btn2.setOnAction(e->{
            if(userForm.apply()){
                Scene s = POPS.getSceneManager().getPreviousScene();
                if(s instanceof UsersScene)
                    ((UsersScene)s).refresh();
                POPS.getSceneManager().prevScene();
            }
        });
        HBox.setHgrow(space, Priority.ALWAYS);
        hBox.getChildren().addAll(btn1, space, btn2);
        vBox.getChildren().add(hBox);
        root.getChildren().add(vBox);
    }

}
