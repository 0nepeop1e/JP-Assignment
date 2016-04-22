package com.shopmart.pops.database.entities;

import java.sql.ResultSet;

/**
 * Entry of an entity.
 */
public abstract class Entry {

    boolean validInDatabase = false;
    /**
     * Is this entry valid in database? Not valid
     * when the entry is yet inserted or deleted
     * from database.
     * @return valid?
     */
    public boolean isValidInDatabase(){
        return this.validInDatabase;
    }

    /**
     * Use for assign field with data from result
     * set, this function can be override.
     * @param set the set
     */
    protected void inject(ResultSet set){

    }
}
