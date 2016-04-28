package com.shopmart.pops.components.controls.finders;

import com.shopmart.pops.POPS;
import com.shopmart.pops.components.abstracts.DateStatusFilter;
import com.shopmart.pops.manager.data.OrderManager;
import com.shopmart.pops.manager.data.entries.Order;
import com.shopmart.pops.manager.data.enums.OrderStatus;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;

import java.time.LocalDateTime;

/**
 * Created by 0nepeop1e on 4/28/16.
 */
public class OrderFinder  extends DateStatusFilter<Order, OrderStatus, OrderManager> {
    public OrderFinder(){
        super(POPS.getDataManager().getOrderManager(), OrderStatus.values());
    }

    @Override
    protected TableColumn<Order, ?>[] getColumns() {
        TableColumn<Order, LocalDateTime> c1 = new TableColumn<>("Time");
        TableColumn<Order, String> c2 = new TableColumn<>("Creator");
        TableColumn<Order, String> c2_1 = new TableColumn<>("Staff ID");
        TableColumn<Order, String> c2_2 = new TableColumn<>("Staff Name");
        TableColumn<Order, String> c3 = new TableColumn<>("Supplier");
        TableColumn<Order, String> c3_1 = new TableColumn<>("Name");
        TableColumn<Order, String> c3_2 = new TableColumn<>("Email");
        TableColumn<Order, String> c4 = new TableColumn<>("Status");
        c1.setCellValueFactory(d->new SimpleObjectProperty<>(d.getValue().getTime()));
        c2_1.setCellValueFactory(d->new SimpleStringProperty(
                d.getValue().getCreator().getStaffId()));
        c2_2.setCellValueFactory(d->new SimpleStringProperty(
                d.getValue().getCreator().getStaffName()));
        c2.getColumns().addAll(c2_1, c2_2);
        c3_1.setCellValueFactory(d->new SimpleStringProperty(
                d.getValue().getSupplier().getName()));
        c3_2.setCellValueFactory(d->new SimpleStringProperty(
                d.getValue().getSupplier().getEmail()));
        c3.getColumns().addAll(c3_1, c3_2);
        c4.setCellValueFactory(d->new SimpleStringProperty(
                d.getValue().getStatus().toString()));
        return new TableColumn[]{c1, c2, c3, c4};
    }
}
