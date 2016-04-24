package com.shopmart.pops.database.manager;

import com.shopmart.pops.database.annotations.AutoQuery;
import com.shopmart.pops.database.entities.Column;

import java.util.Collection;

/**
 * Created by 0nepeop1e on 4/23/16.
 */
public abstract class ColumnManager implements DataManager<Column> {
    @AutoQuery("pragma table_info('%s');")
    public abstract Collection<Column> getFields(String name);
}
