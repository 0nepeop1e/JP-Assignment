package com.shopmart.pops.database.manager;

import com.shopmart.pops.database.entities.Table;
import com.shopmart.pops.database.annotations.AutoQuery;

import java.util.Collection;

public abstract class TableManager extends DataManager<Table> {
    @Override @AutoQuery("select name, sql from sqlite_master where type='table' and name <> 'sqlite_sequence';")
    public abstract Collection<Table> getAll();
}
