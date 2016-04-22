package com.shopmart.pops.database;

import lombok.Getter;

/**
 * A class which represent criteria
 */
public class Criteria {
    @Getter
    String sql;
    @Getter
    Object[] params;
    private Criteria(String sql, Object[] params){
        this.sql = sql;
        this.params = params;
    }
}
