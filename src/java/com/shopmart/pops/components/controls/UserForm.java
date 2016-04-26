package com.shopmart.pops.components.controls;

import com.shopmart.pops.POPS;
import com.shopmart.pops.manager.data.enums.AccessLevel;
import com.shopmart.pops.manager.data.objects.User;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by 0nepeop1e on 4/27/16.
 */
public class UserForm extends GridPane {
    private User user;
    private TextField usernameField;
    private TextField staffIdField;
    private TextField staffNameField;
    private ComboBox<AccessLevel> accessLevelField;
    public UserForm(User user){
        this.user = user;
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
        Button pwd = new Button("Change Password");
        pwd.setDisable(!cu.getAccessLevel().enoughFor(AccessLevel.Terminated));
        pwd.setOnAction(e->setPassword());
        this.add(pwd, 1, 1);
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

    private void setPassword(){

    }
}
