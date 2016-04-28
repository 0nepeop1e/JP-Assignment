package com.shopmart.pops.components.scenes;

import com.shopmart.pops.POPS;
import com.shopmart.pops.components.controls.CredentialForm;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.regex.Pattern;

/**
 * I think the class name is already descriptive enough.
 */
public class LoginScene extends Scene {

    private CredentialForm credential;

    /**
     * Just a constructor, I don't think any
     * explanation required for this, lol.
     */
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
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Login");
        a.initOwner(POPS.getSceneManager().getStage());
        String username = credential.getUsername();
        String password = credential.getPassword();
        if(Pattern.matches("^\\s*$", username)){
            a.setHeaderText("Invalid Username!");
            a.setContentText("Please enter a valid username.");
            a.showAndWait();
            credential.setUsername("");
            credential.setPassword("");
            credential.getUsernameField().requestFocus();
            return;
        }
        if(Pattern.matches("^\\s*$", password)){
            a.setHeaderText("Invalid Password!");
            a.setContentText("Please enter a valid password.");
            a.showAndWait();
            credential.setPassword("");
            credential.getPasswordField().requestFocus();
            return;
        }
        if(POPS.getDataManager().getUserManager().login(username, password)) {
            POPS.getSceneManager().nextScene(new MenuScene());
            credential.setUsername("");
            credential.setPassword("");
            return;
        }
        a.setHeaderText("Failed to Login!");
        a.setContentText("Incorrect username or password.");
        a.showAndWait();
        credential.setPassword("");
    }
}
