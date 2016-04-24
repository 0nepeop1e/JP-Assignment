package com.shopmart.pops.database.manager;

import com.shopmart.pops.database.EasySQLite;
import com.shopmart.pops.database.entities.Entry;

import java.sql.ResultSet;

/**
 * Manager to manage table
 */
public interface DataManager<T extends Entry> {
    /**
     * Get injected entry by the giving result set.
     * @param rs the result set
     * @return the entry
     */
    T getInjectedEntry(ResultSet rs);

    EasySQLite getDatabase();
}
