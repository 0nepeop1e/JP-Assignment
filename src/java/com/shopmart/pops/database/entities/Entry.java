package com.shopmart.pops.database.entities;

import com.shopmart.pops.database.annotations.AssignWithField;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        for(Field f : this.getClass().getFields()){
            AssignWithField a;
            if((a = f.getAnnotation(AssignWithField.class)) != null)
                try {
                    f.setAccessible(true);
                    f.set(this, a.value().isEmpty() ?
                            set.getObject(f.getName()):
                            set.getObject(a.value()));
                } catch (IllegalAccessException | SQLException e) {
                    e.printStackTrace();
                }
        }
    }
}
