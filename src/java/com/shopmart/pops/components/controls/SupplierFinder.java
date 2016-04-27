package com.shopmart.pops.components.controls;

import com.shopmart.pops.POPS;
import com.shopmart.pops.components.abstracts.AbstractFinder;
import com.shopmart.pops.components.abstracts.SearchFactory;
import com.shopmart.pops.components.abstracts.SearchFunction;
import com.shopmart.pops.manager.data.SupplierManager;
import com.shopmart.pops.manager.data.entries.Supplier;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;

/**
 * Created by 0nepeop1e on 4/27/16.
 */
public class SupplierFinder extends AbstractFinder<Supplier, SupplierFinder.SearchBy>{

    public SupplierFinder(){
        super(SearchBy.values(), SearchBy.Name);
    }

    @Override
    protected TableColumn<Supplier, ?>[] getColumns() {
        TableColumn<Supplier, String> c1 = new TableColumn<>("Name");
        TableColumn<Supplier, String> c2 = new TableColumn<>("Contact");
        TableColumn<Supplier, String> c3 = new TableColumn<>("Email");
        TableColumn<Supplier, String> c4 = new TableColumn<>("Phone");
        TableColumn<Supplier, String> c5 = new TableColumn<>("Fax");
        c1.setCellValueFactory(cd->new SimpleStringProperty(cd.getValue().getName()));
        c3.setCellValueFactory(cd->new SimpleStringProperty(cd.getValue().getEmail()));
        c4.setCellValueFactory(cd->new SimpleStringProperty(cd.getValue().getPhone()));
        c5.setCellValueFactory(cd->new SimpleStringProperty(cd.getValue().getFax()));
        c2.getColumns().addAll(c3, c4, c5);
        return new TableColumn[]{c1, c2};
    }

    @Override
    protected SupplierManager getManager() {
        return POPS.getDataManager().getSupplierManager();
    }

    enum SearchBy implements SearchFactory<Supplier, SupplierManager>{
        Name {
            @Override
            public SearchFunction<Supplier, SupplierManager> getFunction() {
                return SupplierManager::findByName;
            }
        },
        Email {
            @Override
            public SearchFunction<Supplier, SupplierManager> getFunction() {
                return SupplierManager::findByEmail;
            }
        },
        Phone {
            @Override
            public SearchFunction<Supplier, SupplierManager> getFunction() {
                return SupplierManager::findByPhone;
            }
        },
        Fax {
            @Override
            public SearchFunction<Supplier, SupplierManager> getFunction() {
                return SupplierManager::findByFax;
            }
        };
        @Override
        public String toString(){
            if(this == Phone) return "Phone Number";
            return super.toString();
        }
    }
}
