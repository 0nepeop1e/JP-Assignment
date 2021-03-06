package com.shopmart.pops.components.controls.finders;

import com.shopmart.pops.POPS;
import com.shopmart.pops.components.abstracts.DateStatusFilter;
import com.shopmart.pops.manager.data.RequestManager;
import com.shopmart.pops.manager.data.entries.Request;
import com.shopmart.pops.manager.data.enums.RequestStatus;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;

import java.time.LocalDateTime;

/**
 * Created by 0nepeop1e on 4/28/16.
 */
public class RequestFinder extends DateStatusFilter<Request, RequestStatus, RequestManager> {
    public RequestFinder(){
        super(POPS.getDataManager().getRequestManager(), RequestStatus.values());
    }

    @Override
    protected TableColumn<Request, ?>[] getColumns() {
        TableColumn<Request, LocalDateTime> c1 = new TableColumn<>("Time");
        TableColumn<Request, String> c2 = new TableColumn<>("Creator");
        TableColumn<Request, String> c2_1 = new TableColumn<>("Staff ID");
        TableColumn<Request, String> c2_2 = new TableColumn<>("Staff Name");
        TableColumn<Request, String> c3 = new TableColumn<>("Status");
        TableColumn<Request, String> c3_1 = new TableColumn<>("status");
        TableColumn<Request, String> c3_2 = new TableColumn<>("by (ID)");
        TableColumn<Request, String> c3_3 = new TableColumn<>("by (Name)");
        TableColumn<Request, String> c4 = new TableColumn<>("Remarks");
        c1.setCellValueFactory(d->new SimpleObjectProperty<>(d.getValue().getTime()));
        c2_1.setCellValueFactory(d->new SimpleStringProperty(
                d.getValue().getCreator().getStaffId()));
        c2_2.setCellValueFactory(d->new SimpleStringProperty(
                d.getValue().getCreator().getStaffName()));
        c2.getColumns().addAll(c2_1, c2_2);
        c3_1.setCellValueFactory(d->new SimpleStringProperty(
                d.getValue().getStatus().toString()));
        c3_2.setCellValueFactory(d->new SimpleStringProperty(
                d.getValue().getModifier().getStaffId()));
        c3_3.setCellValueFactory(d->new SimpleStringProperty(
                d.getValue().getModifier().getStaffName()));
        c3.getColumns().addAll(c3_1, c3_2, c3_3);
        c4.setCellValueFactory(d->new SimpleStringProperty(d.getValue().getRemarks()));
        return new TableColumn[]{c1, c2, c3, c4};
    }
}
