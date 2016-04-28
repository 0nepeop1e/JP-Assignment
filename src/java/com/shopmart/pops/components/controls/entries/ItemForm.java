package com.shopmart.pops.components.controls.entries;

import com.shopmart.pops.components.controls.finders.SupplierFinder;
import com.shopmart.pops.components.dialogs.FinderDialog;
import com.shopmart.pops.manager.data.entries.Item;
import com.shopmart.pops.manager.data.entries.Supplier;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import lombok.Getter;

import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Created by 0nepeop1e on 4/28/16.
 */
public class ItemForm extends GridPane {

    private TextField nameField, barcodeField, unitField, remarksField;
    private SupplierForm supplierForm;
    @Getter
    private Item item;

    public ItemForm(Item item){
        this.item = item;
        this.setHgap(8);
        this.setVgap(8);
        this.add(new Label("Name:"), 0, 0);
        this.add(new Label("Barcode:"), 0, 1);
        this.add(new Label("Unit:"), 0, 2);
        this.add(new Label("Supplier:"), 0, 3);
        this.add(new Label("Remarks"), 0, 5);
        this.nameField = new TextField(item.getName());
        this.barcodeField = new TextField(item.getBarcode());
        this.unitField = new TextField(item.getUnit());
        Button button = new Button(item.getSupplier() == null ?
                "Change Supplier" : "Set Supplier");
        button.setOnAction(e->{
            FinderDialog<Supplier, SupplierFinder> fd =
                    new FinderDialog<>(new SupplierFinder());
            fd.showAndWait();
            Optional<Supplier> os = fd.getSelectedItem();
            if(os.isPresent())
                supplierForm.setSupplier(os.get());
            button.setText(supplierForm.getSupplier() == null ?
                    "Change Supplier" : "Set Supplier");
        });
        this.supplierForm = new SupplierForm(item.getSupplier());
        this.supplierForm.setDisable(true);
        this.supplierForm.setStyle("-fx-font-size:9pt;");
        this.remarksField = new TextField(item.getRemarks());
        this.add(nameField, 1, 0);
        this.add(barcodeField, 1, 1);
        this.add(unitField, 1, 2);
        this.add(button, 1, 3);
        this.add(supplierForm, 1, 4);
        this.add(remarksField, 1, 5);
    }

    public boolean apply(){
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.initOwner(this.getScene().getWindow());
        a.setTitle("Item");
        if(Pattern.matches("^\\s*$", nameField.getText())){
            a.setHeaderText("Invalid Name!");
            a.setContentText("Name cannot be empty.");
            a.showAndWait();
            return false;
        }
        if(Pattern.matches("^\\D*$", barcodeField.getText())){
            a.setHeaderText("Invalid Barcode!");
            a.setContentText("Please enter a valid barcode.");
            a.showAndWait();
            return false;
        }
        if(Pattern.matches("^\\s*$", unitField.getText())){
            a.setHeaderText("Invalid Unit!");
            a.setContentText("Unit cannot be empty.");
            a.showAndWait();
            return false;
        }
        if(supplierForm.getSupplier() == null){
            a.setHeaderText("No Supplier!");
            a.setContentText("Please set a valid supplier.");
            a.showAndWait();
            return false;
        }
        item.setName(nameField.getText());
        item.setBarcode(barcodeField.getText());
        item.setUnit(unitField.getText());
        item.setSupplier(supplierForm.getSupplier().getId());
        item.setRemarks(remarksField.getText());
        return true;
    }
}
