package com.shopmart.pops.database.manager;

import com.shopmart.pops.database.Criteria;
import com.shopmart.pops.database.entities.Entry;
import com.shopmart.pops.database.annotations.AutoQuery;

import java.sql.ResultSet;
import java.util.Collection;

/**
 * Manager to manage table
 */
public abstract class DataManager<T extends Entry> {
    /**
     * Insert an entry to the table.
     * @param entry the entry
     */
    @AutoQuery
    public abstract void insert(T entry);
    /**
     * Update an entry in the table.
     * @param entry the entry
     */
    @AutoQuery
    public abstract void update(T entry);
    /**
     * Delete an entry from the table.
     * @param entry the entry
     */
    @AutoQuery
    public abstract void delete(T entry);

    /**
     * Delete an entry which match the criteria.
     * @param criteria the criteria
     */
    @AutoQuery
    public abstract void delete(Criteria criteria);

    /**
     * Get all entries in the table
     */
    @AutoQuery
    public abstract Collection<T> getAll();

    /**
     * Count the entries in the table.
     * @return the result
     */
    @AutoQuery
    public abstract int count();

    /**
     * Count the entries in the table which match the criteria.
     * @param criteria the criteria
     * @return the result;
     */
    @AutoQuery
    public abstract int count(Criteria criteria);

    /**
     * Get injected entry by the giving result set.
     * @param rs the result set
     * @return the entry
     */
    protected abstract T getInjectedEntry(ResultSet rs);
}
