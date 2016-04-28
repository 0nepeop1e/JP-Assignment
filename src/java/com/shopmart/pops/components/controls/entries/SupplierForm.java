package com.shopmart.pops.components.controls.entries;

import com.shopmart.pops.manager.data.entries.Supplier;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import lombok.Getter;

import java.util.regex.Pattern;

/**
 * Created by 0nepeop1e on 4/28/16.
 */
public class SupplierForm extends GridPane {

    @Getter
    private Supplier supplier;

    private TextField nameField, emailField, phoneField, faxField;
    private TextArea addressField;

    public SupplierForm(Supplier supplier){
        this.setHgap(8);
        this.setVgap(8);
        this.add(new Label("Name:"), 0, 0);
        this.add(new Label("Address:"), 0, 1);
        this.add(new Label("E-mail:"), 0, 2);
        this.add(new Label("Phone:"), 0, 3);
        this.add(new Label("Fax:"), 0, 4);
        nameField = new TextField();
        addressField = new TextArea();
        addressField.setPrefRowCount(4);
        addressField.setPrefWidth(320);
        emailField = new TextField();
        phoneField = new TextField();
        faxField = new TextField();
        this.add(nameField, 1, 0);
        this.add(addressField, 1 ,1);
        this.add(emailField, 1, 2);
        this.add(phoneField, 1, 3);
        this.add(faxField, 1, 4);
        this.disabledProperty().addListener((v, o, n)->{
            nameField.setDisable(n);
            addressField.setDisable(n);
            emailField.setDisable(n);
            phoneField.setDisable(n);
            faxField.setDisable(n);
        });
        this.setSupplier(supplier);
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
        if(supplier != null){
            nameField.setText(supplier.getName());
            addressField.setText(supplier.getAddress());
            emailField.setText(supplier.getEmail());
            phoneField.setText(supplier.getPhone());
            faxField.setText(supplier.getFax());
        }else{
            nameField.clear();
            addressField.clear();
            emailField.clear();
            phoneField.clear();
            faxField.clear();
        }
    }

    public boolean apply(){
        if(supplier == null) return false;
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.initOwner(this.getScene().getWindow());
        a.setTitle("Supplier");
        if(Pattern.matches("^\\s*$", nameField.getText())){
            a.setHeaderText("Invalid Name!");
            a.setContentText("Please enter a valid name.");
            a.showAndWait();
            return false;
        }
        if(Pattern.matches("^\\s*$", addressField.getText())){
            a.setHeaderText("Invalid Address!");
            a.setContentText("Please enter a valid address.");
            a.showAndWait();
            return false;
        }

        if(!Pattern.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$", emailField.getText())){
            a.setHeaderText("Invalid Email!");
            a.setContentText("Please enter a valid email.");
            a.showAndWait();
            return false;
        }
        if(!Pattern.matches("^(\\(\\+\\d+\\))?\\d+$", phoneField.getText())){
            a.setHeaderText("Invalid Phone Number!");
            a.setContentText("Please enter a valid phone number.");
            a.showAndWait();
            return false;
        }
        if(!Pattern.matches("^(\\(\\+\\d+\\))?\\d+$", faxField.getText())){
            a.setHeaderText("Invalid Fax Number!");
            a.setContentText("Please enter a valid fax number.");
            a.showAndWait();
            return false;
        }
        supplier.setName(nameField.getText());
        supplier.setAddress(addressField.getText());
        supplier.setEmail(emailField.getText());
        supplier.setPhone(phoneField.getText());
        supplier.setFax(faxField.getText());
        return true;
    }

}
