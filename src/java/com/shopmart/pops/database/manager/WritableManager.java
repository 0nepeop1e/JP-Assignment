package com.shopmart.pops.database.manager;

import com.shopmart.pops.database.annotations.AutoQuery;
import com.shopmart.pops.database.entities.Entry;

/**
 * Created by 0nepeop1e on 4/23/16.
 */
public interface WritableManager<T extends Entry> extends DataManager<T> {

    /**
     * Insert an entry to the table.
     * @param entry the entry
     */
    @AutoQuery
    void insert(T entry);
    /**
     * Update an entry in the table.
     * @param entry the entry
     */
    @AutoQuery
    void update(T entry);
    /**
     * Delete an entry from the table.
     * @param entry the entry
     */
    @AutoQuery
    void delete(T entry);
}
