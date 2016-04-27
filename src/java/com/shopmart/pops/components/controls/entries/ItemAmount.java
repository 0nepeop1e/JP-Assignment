package com.shopmart.pops.components.controls.entries;

import com.shopmart.pops.manager.data.entries.Item;
import javafx.beans.property.*;
import lombok.Getter;

/**
 * Created by 0nepeop1e on 4/28/16.
 */
public class ItemAmount {
    private final ReadOnlyStringProperty
            name, barcode, unit, supplierName, supplierEmail;
    private final IntegerProperty amount;
    @Getter
    private final Item item;
    public ItemAmount(Item item){
        this.item = item;
        this.name = new ReadOnlyStringWrapper(item.getName());
        this.barcode = new ReadOnlyStringWrapper(item.getBarcode());
        this.unit = new ReadOnlyStringWrapper(item.getUnit());
        this.supplierName = new ReadOnlyStringWrapper(item.getSupplier().getName());
        this.supplierEmail = new ReadOnlyStringWrapper(item.getSupplier().getEmail());
        this.amount = new SimpleIntegerProperty(0);
        this.amount.addListener((v, o, n)->{
            if((int)n < 1) this.setAmount(1);});
    }

    public ItemAmount(Item item, int amount){
        this(item);
        this.setAmount(amount);
    }

    public int getAmount(){
        return this.amount.get();
    }

    public void setAmount(int amount){
        this.amount.set(amount);
    }

    public ReadOnlyStringProperty nameProperty(){
        return name;
    }

    public ReadOnlyStringProperty barcodeProperty(){
        return barcode;
    }

    public ReadOnlyStringProperty unitProperty(){
        return unit;
    }

    public ReadOnlyStringProperty supplierNameProperty(){
        return supplierName;
    }

    public ReadOnlyStringProperty supplierEmailProperty(){
        return supplierEmail;
    }

    public IntegerProperty amountProperty(){
        return amount;
    }
}
