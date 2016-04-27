package com.shopmart.pops.components.controls;

import com.shopmart.pops.POPS;
import com.shopmart.pops.components.abstracts.AbstractFinder;
import com.shopmart.pops.components.abstracts.SearchFactory;
import com.shopmart.pops.components.abstracts.SearchFunction;
import com.shopmart.pops.manager.data.UserManager;
import com.shopmart.pops.manager.data.entries.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.*;

/**
 * Created by 0nepeop1e on 4/27/16.
 */
public class UserFinder extends AbstractFinder<User, UserFinder.SearchBy> {
    public UserFinder(){
        super(SearchBy.values(), SearchBy.Username);
    }

    @Override
    protected TableColumn<User, ?>[] getColumns() {
        TableColumn<User, String> c1 = new TableColumn<>("Username");
        TableColumn<User, String> c2 = new TableColumn<>("Staff ID");
        TableColumn<User, String> c3 = new TableColumn<>("Staff Name");
        TableColumn<User, String> c4 = new TableColumn<>("Access Level");
        c1.setCellValueFactory(cd->new SimpleStringProperty(
                cd.getValue().getUsername()));
        c2.setCellValueFactory(cd->new SimpleStringProperty(
                cd.getValue().getStaffId()));
        c3.setCellValueFactory(cd->new SimpleStringProperty(
                cd.getValue().getStaffName()));
        c4.setCellValueFactory(cd->new SimpleStringProperty(
                cd.getValue().getAccessLevel().toString()));
        return new TableColumn[]{c1, c2, c3, c4};
    }

    @Override
    protected UserManager getManager() {
        return POPS.getDataManager().getUserManager();
    }

    enum SearchBy implements SearchFactory<User, UserManager>{
        Username, StaffId, StaffName, AccessLevel;
        @Override
        public String toString(){
            if(this == StaffId) return "Staff ID";
            if(this == StaffName) return "Staff Name";
            if(this == AccessLevel) return "Access Level";
            return super.toString();
        }
        @Override
        public SearchFunction<User, UserManager> getFunction(){
            switch (this){
                case Username:
                    return UserManager::findByUsername;
                case StaffId:
                    return UserManager::findByStaffId;
                case StaffName:
                    return UserManager::findByUsername;
                case AccessLevel:
                    return UserManager::findByAccessLevel;
            }
            return null;
        }
    }
}
