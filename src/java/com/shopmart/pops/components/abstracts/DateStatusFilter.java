package com.shopmart.pops.components.abstracts;

import com.shopmart.pops.manager.data.abstracts.AbstractEntry;
import com.shopmart.pops.manager.data.abstracts.TimeStatusManager;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

/**
 * Created by 0nepeop1e on 4/28/16.
 */
public abstract class DateStatusFilter<T extends AbstractEntry, S extends Enum,
        M extends TimeStatusManager<T, S>> extends VBox {

    protected M manager;
    protected TableView<T> dataView;
    protected ComboBox<FilterBy> filterBy;
    protected ComboBox<S> status;
    protected DatePicker date1, date2;

    public DateStatusFilter(M manager, S[] status){
        this.manager = manager;
        this.setSpacing(8);
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(8);
        Label l = new Label("Filter by:");
        HBox.setHgrow(l, Priority.NEVER);
        filterBy = new ComboBox<>();
        filterBy.setEditable(false);
        filterBy.getItems().addAll(FilterBy.values());
        filterBy.setValue(FilterBy.DateAfter);
        filterBy.valueProperty().addListener((v, o, n)->changeFilter());
        HBox.setHgrow(filterBy, Priority.NEVER);
        LocalDate today = LocalDate.now();
        date1 = new DatePicker(today.withDayOfMonth(1));
        date1.setConverter(new DateFormat());
        HBox.setHgrow(date1, Priority.ALWAYS);
        date2 = new DatePicker(today.withDayOfMonth(today.lengthOfMonth()));
        date2.setConverter(new DateFormat());
        date2.setVisible(false);
        HBox.setHgrow(date2, Priority.ALWAYS);
        this.status = new ComboBox<>();
        this.status.setEditable(false);
        this.status.getItems().addAll(status);
        this.status.setValue(status[0]);
        this.status.setVisible(false);
        HBox.setHgrow(this.status, Priority.ALWAYS);
        Button apply = new Button("Apply");
        apply.setOnAction(e->refresh());
        HBox.setHgrow(apply, Priority.NEVER);
        hBox.getChildren().addAll(l, filterBy, date1, date2, this.status, apply);
        VBox.setVgrow(hBox, Priority.NEVER);
        dataView = new TableView<>();
        dataView.setEditable(false);
        dataView.getColumns().addAll(getColumns());
        VBox.setVgrow(dataView, Priority.ALWAYS);
        this.getChildren().addAll(hBox, dataView);
        refresh();
    }

    public void refresh(){
        Collection<T> cache = null;
        switch (filterBy.getValue()){
            case DateAfter:
                cache = manager.getAllAfter(date1.getValue().atTime(0, 0));
                break;
            case DateBefore:
                cache = manager.getAllBefore(date1.getValue().atTime(0, 0));
                break;
            case DateBetween:
                LocalDate min = date1.getValue().compareTo(date2.getValue()) <= 0 ?
                        date1.getValue() : date2.getValue();
                LocalDate max = date1.getValue().compareTo(date2.getValue()) > 0 ?
                        date1.getValue() : date2.getValue();
                cache = manager.getAllBetween(min.atTime(0, 0), max.atTime(13, 59, 59));
                break;
            case Status:
                cache = manager.getAllByStatus(status.getValue());
                break;
        }
        dataView.getItems().removeAll(dataView.getItems());
        dataView.getItems().addAll(cache);
    }

    private void changeFilter() {
        switch (filterBy.getValue()){
            case DateAfter: case DateBefore:
                date1.setVisible(true);
                date2.setVisible(false);
                status.setVisible(false);
                break;
            case DateBetween:
                date1.setVisible(true);
                date2.setVisible(true);
                status.setVisible(false);
                break;
            case Status:
                date1.setVisible(false);
                date2.setVisible(false);
                status.setVisible(true);
                break;
        }
    }

    public T getSelectedItem(){
        return dataView.getSelectionModel().getSelectedItem();
    }

    protected abstract TableColumn<T, ?>[] getColumns();

    protected enum FilterBy{
        DateAfter, DateBefore, DateBetween, Status;
        @Override
        public String toString(){
            switch (this){
                case DateAfter:return "Date After";
                case DateBefore:return "Date Before";
                case DateBetween:return "Date Between";
            }
            return super.toString();
        }
    }

    private static class DateFormat extends StringConverter<LocalDate> {

        private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("d MMM yyyy");

        @Override
        public String toString(LocalDate object) {
            if(object != null)
                return format.format(object);
            return "";
        }

        @Override
        public LocalDate fromString(String string) {
            try{
                return LocalDate.parse(string, format);
            }catch (Exception e){}
            return null;
        }
    }
}
