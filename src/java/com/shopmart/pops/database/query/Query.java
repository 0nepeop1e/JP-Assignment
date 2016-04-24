package com.shopmart.pops.database.query;

import lombok.Getter;

/**
 * Created by 0nepeop1e on 4/23/16.
 */
public class Query {
    @Getter
    protected Object[] params;
    @Getter
    protected String sql;
    public Query(String sql, Object ...params){
        this.sql = sql;
        this.params = params;
    }
}
