package com.shopmart.pops.manager.data.entries;

import com.shopmart.pops.POPS;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import lombok.Getter;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

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

    public static void toMap(Collection<ItemAmount> amounts, Map<Integer, Integer> map){
        map.clear();
        amounts.forEach(a->map.put(a.getItem().getId(), a.getAmount()));
    }

    public static Collection<ItemAmount> fromMap(Map<Integer, Integer> map){
        return map.entrySet().stream().map(e->new ItemAmount(
                POPS.getDataManager().getItemManager().getById(
                e.getKey()), e.getValue())).collect(Collectors.toList());
    }
}
