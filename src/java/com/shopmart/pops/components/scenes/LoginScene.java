package com.shopmart.pops.components.scenes;

import com.shopmart.pops.POPS;
import com.shopmart.pops.components.controls.CredentialForm;
import com.shopmart.pops.components.dialogs.CredentialDialog;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * I think the class name is already descriptive enough.
 */
public class LoginScene extends Scene {

    private CredentialForm credential;

    public LoginScene(){
        super(new HBox());
        HBox root = (HBox) this.getRoot();
        root.setAlignment(Pos.CENTER);
        VBox pane = new VBox();
        root.getChildren().add(pane);
        pane.setStyle("-fx-font-size:12pt;");
        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(32);
        Label txt1 = new Label("Login");
        txt1.setStyle("-fx-font-size:32pt;");
        txt1.setAlignment(Pos.CENTER);
        Button btn_login = new Button("Login");
        btn_login.setPrefWidth(96);
        btn_login.setOnAction(e->login());
        btn_login.setDefaultButton(true);
        Button btn_exit = new Button("Exit");
        btn_exit.setPrefWidth(96);
        btn_exit.setOnAction(
                (e) -> POPS.getSceneManager().prevScene());
        credential = new CredentialForm();
        HBox box = new HBox();
        box.setSpacing(4);
        box.getChildren().addAll(btn_login, btn_exit);
        box.setAlignment(Pos.CENTER_RIGHT);
        pane.getChildren().addAll(txt1, credential, box);
    }
    private void login(){
        String username = credential.getUsername();
        String password = credential.getPassword();
        CredentialDialog cd = new CredentialDialog("");
        cd.initOwner(POPS.getSceneManager().getStage());
        cd.showAndWait();
    }
}
