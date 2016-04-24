package com.shopmart.pops.database.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 0nepeop1e on 4/24/16.
 */
public class QueryBuilder {
    private String sql;
    private List<Object> params;
    public QueryBuilder(String sql, Object ...params){
        this.sql = sql;
        this.params = Arrays.asList(params);
    }
    public QueryBuilder append(String sql, Object[] ...params){
        this.sql += sql;
        this.params.addAll(Arrays.asList(params));
        return this;
    }
    public QueryBuilder append(Query query){
        append(query.getSql(), query.getParams());
        return this;
    }
    public QueryBuilder appendNested(String sql, Object ...params){
        append(String.format("(%s)", sql), params);
        return this;
    }
    public QueryBuilder appendNested(Query query){
        appendNested(query.getSql(), query.getParams());
        return this;
    }

    public Query build(){
        return new Query(this.sql, this.params.toArray());
    }
}
