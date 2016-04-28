package com.shopmart.pops.components.controls.finders;

import com.shopmart.pops.POPS;
import com.shopmart.pops.components.abstracts.AbstractFinder;
import com.shopmart.pops.components.abstracts.SearchFactory;
import com.shopmart.pops.components.abstracts.SearchFunction;
import com.shopmart.pops.manager.data.ItemManager;
import com.shopmart.pops.manager.data.entries.Item;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;

/**
 * Created by 0nepeop1e on 4/27/16.
 */
public class ItemFinder extends AbstractFinder<Item, ItemFinder.SearchBy> {

    public ItemFinder(){
        super(SearchBy.values(), SearchBy.Name);
    }

    @Override
    protected TableColumn<Item, ?>[] getColumns() {
        TableColumn<Item, String> c1 = new TableColumn<>("Name");
        TableColumn<Item, String> c2 = new TableColumn<>("Barcode");
        TableColumn<Item, String> c3 = new TableColumn<>("Unit");
        TableColumn<Item, String> c4 = new TableColumn<>("Supplier");
        TableColumn<Item, String> c4_1 = new TableColumn<>("Name");
        TableColumn<Item, String> c4_2 = new TableColumn<>("E-mail");
        TableColumn<Item, String> c5 = new TableColumn<>("Remarks");
        c1.setCellValueFactory(d->new SimpleStringProperty(d.getValue().getName()));
        c2.setCellValueFactory(d->new SimpleStringProperty(d.getValue().getBarcode()));
        c3.setCellValueFactory(d->new SimpleStringProperty(d.getValue().getUnit()));
        c4_1.setCellValueFactory(d->new SimpleStringProperty(
                d.getValue().getSupplier().getName()));
        c4_2.setCellValueFactory(d->new SimpleStringProperty(
                d.getValue().getSupplier().getEmail()));
        c5.setCellValueFactory(d->new SimpleStringProperty(d.getValue().getRemarks()));
        c4.getColumns().addAll(c4_1, c4_2);
        return new TableColumn[]{c1, c2, c3, c4, c5};
    }

    @Override
    protected ItemManager getManager() {
        return POPS.getDataManager().getItemManager();
    }

    enum SearchBy implements SearchFactory<Item, ItemManager>{
        Name, Barcode, Supplier;
        @Override
        public SearchFunction<Item, ItemManager> getFunction(){
            switch (this){
                case Name:
                    return ItemManager::findByName;
                case Barcode:
                    return ItemManager::findByBarcode;
                case Supplier:
                    return ItemManager::findBySupplier;
            }
            return null;
        }
    }
}
