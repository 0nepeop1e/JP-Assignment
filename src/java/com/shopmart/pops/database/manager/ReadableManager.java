package com.shopmart.pops.database.manager;

import com.shopmart.pops.database.annotations.AutoQuery;
import com.shopmart.pops.database.entities.Entry;

import java.util.Collection;

/**
 * Created by 0nepeop1e on 4/23/16.
 */
public interface ReadableManager<T extends Entry> extends DataManager<T> {

    /**
     * Get all entries in the table.
     * @return the result
     */
    @AutoQuery
    Collection<T> getAll();

    /**
     * Count the entries in the table.
     * @return the result
     */
    @AutoQuery
    int count();
}
