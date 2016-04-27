package com.shopmart.pops.components.dialogs;

import javafx.beans.NamedArg;
import javafx.event.Event;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import sun.security.util.Password;

import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Created by 0nepeop1e on 4/27/16.
 */
public class PasswordDialog extends Alert {

    private PasswordField pwd1, pwd2;
    private boolean stopClose = false;
    @Getter
    private Optional<String> password;

    public PasswordDialog() {
        super(AlertType.INFORMATION);
        password = Optional.empty();
        this.getButtonTypes().clear();
        this.getButtonTypes().addAll(ButtonType.OK,ButtonType.CANCEL);
        this.setTitle("Password");
        this.setHeaderText("Please type a password.");
        GridPane gp = new GridPane();
        gp.setHgap(8);
        gp.setVgap(8);
        Label l1 = new Label("Password:");
        Label l2 = new Label("Confirm Password:");
        gp.add(l1, 0, 0);
        gp.add(l2, 1, 0);
        pwd1 = new PasswordField();
        pwd2 = new PasswordField();
        gp.add(pwd1, 0, 1);
        gp.add(pwd2, 1, 1);
        this.setOnCloseRequest(e->{
            if(stopClose) e.consume();
            stopClose = false;
        });
        Button btnOK = (Button) this.getDialogPane().lookupButton(ButtonType.OK);
        btnOK.setOnAction(e->onOK());
    }

    private void onOK() {
        Alert a = new Alert(AlertType.ERROR);
        a.initOwner(this.getDialogPane().getScene().getWindow());
        a.setTitle("Password");
        if(!Pattern.matches("^(\\w|[-!$%^&*()_+|~=`{}\\[\\]:\";'<>?,./]){6,}$", pwd1.getText())){
            a.setHeaderText("Invalid Password!");
            a.setContentText("Password must at least 6 characters only accepts" +
                    " alphanumeric and symbols");
            a.showAndWait();
            pwd1.clear();
            pwd2.clear();
            pwd1.requestFocus();
            stopClose = true;
            return;
        }
        if(!pwd1.getText().equals(pwd2.getText())){
            a.setHeaderText("Password not Same!");
            a.setContentText("Please type the same password in both field.");
            a.showAndWait();
            pwd2.clear();
            pwd2.requestFocus();
            stopClose = true;
            return;
        }
        password = Optional.of(pwd1.getText());
    }

    public Optional<String> showAndGet(){
        this.showAndWait();
        return this.password;
    }
}
