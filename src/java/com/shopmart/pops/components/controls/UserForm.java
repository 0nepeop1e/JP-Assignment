package com.shopmart.pops.components.controls;

import com.shopmart.pops.POPS;
import com.shopmart.pops.components.dialogs.CredentialDialog;
import com.shopmart.pops.components.dialogs.PasswordDialog;
import com.shopmart.pops.manager.data.enums.AccessLevel;
import com.shopmart.pops.manager.data.objects.User;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by 0nepeop1e on 4/27/16.
 */
public class UserForm extends GridPane {
    protected User user;
    protected TextField usernameField;
    protected Optional<String> password;
    protected Button passwordButton;
    protected TextField staffIdField;
    protected TextField staffNameField;
    protected ComboBox<AccessLevel> accessLevelField;
    public UserForm(User user){
        this.user = user;
        password = Optional.empty();
        Label[] ls = new Label[5];
        ls[0] = new Label("Username:");
        ls[1] = new Label("Passoword:");
        ls[2] = new Label("Staff ID:");
        ls[3] = new Label("Staff Name:");
        ls[4] = new Label("Access Level:");
        for(int i = 0;i < 5;)this.add(ls[i], i++, 0);
        User cu = POPS.getDataManager()
                .getUserManager().getCurrentUser();
        usernameField = new TextField(user.getUsername());
        usernameField.setDisable(!cu.getAccessLevel()
                .enoughFor(AccessLevel.Staff) || user.getId() == 1);
        this.add(usernameField, 0, 1);
        passwordButton = new Button("Change Password");
        passwordButton.setDisable(!cu.getAccessLevel().enoughFor(AccessLevel.Terminated));
        passwordButton.setOnAction(e->setPassword());
        this.add(passwordButton, 1, 1);
        staffIdField = new TextField(user.getStaffId());
        staffIdField.setDisable(!cu.getAccessLevel().enoughFor(AccessLevel.Manager) ||
                user.getId() == 1);
        this.add(staffIdField, 2, 1);
        staffNameField = new TextField(user.getStaffName());
        staffNameField.setDisable(!cu.getAccessLevel()
                .enoughFor(AccessLevel.Manager) || user.getId() == 1);
        this.add(staffNameField, 3, 1);
        accessLevelField = new ComboBox<>();
        accessLevelField.getItems().addAll(Arrays.stream(
                AccessLevel.values()).filter(a->cu.getAccessLevel().enoughFor(a) &&
                a != AccessLevel.SuperUser && a != AccessLevel.NoAccess)
                .collect(Collectors.toList()));
        accessLevelField.setValue(user.getAccessLevel());
        accessLevelField.setEditable(false);
        accessLevelField.setDisable(!cu.getAccessLevel()
                .enoughFor(AccessLevel.Manager) || user.getId() == 1);
        this.add(accessLevelField, 4, 1);
    }

    public String getUsername(){
        return this.usernameField.getText();
    }

    public String getStaffId(){
        return this.staffIdField.getText();
    }

    public String getStaffName(){
        return this.staffNameField.getText();
    }

    public AccessLevel getAccessLevel(){
        return this.accessLevelField.getValue();
    }

    protected void setPassword(){
        password = new PasswordDialog().showAndGet();
    }

    public boolean apply(){
        if(!this.validate()) return false;
        CredentialDialog cd = new CredentialDialog(this.user.getUsername());
        cd.initOwner(this.getScene().getWindow());
        cd.setHeaderText("System needs the password to continue.");
        cd.setUsernameLocked(true);
        if(this.password.isPresent()){
            if(this.user == POPS.getDataManager().getUserManager().getCurrentUser()){
                cd.showAndWait();
                if(!cd.isValidated()) return false;
                this.user.setPassword(this.password.get());
            }
            if(!this.usernameField.getText().equals(this.user.getUsername()))
                this.user.setUsername(this.getUsername(), this.password.get());
        }else if(!this.usernameField.getText().equals(this.user.getUsername())){
            cd.showAndWait();
            if(!cd.isValidated()) return false;
            this.user.setUsername(this.getUsername(), cd.getPassword());
        }
        this.user.setStaffId(this.getStaffId());
        this.user.setStaffName(this.getStaffName());
        this.user.setAccessLevel(this.getAccessLevel());
        POPS.getDataManager().saveTo(POPS.dataPath);
        return true;
    }

    protected boolean validate(){
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("User");
        a.initOwner(this.getScene().getWindow());
        if(!Pattern.matches("^\\w{8,}$", this.getUsername())){
            a.setHeaderText("Invalid Username!");
            a.setContentText("Username only can alphanumeric and at least 8 characters.");
            a.showAndWait();
            return false;
        }
        if(POPS.getDataManager().getUserManager().getByUsername(getUsername())!=null){
            a.setHeaderText("Invalid Username!");
            a.setContentText("This username already exist.");
            a.showAndWait();
            return false;
        }
        return true;
    }
}
