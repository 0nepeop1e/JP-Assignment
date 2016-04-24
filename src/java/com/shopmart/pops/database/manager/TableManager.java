package com.shopmart.pops.database.manager;

import com.shopmart.pops.database.annotations.FullManualQuery;
import com.shopmart.pops.database.annotations.QueryHandler;
import com.shopmart.pops.database.entities.Table;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class TableManager implements ReadableManager<Table> {

    @Override @FullManualQuery("get_all")
    public abstract Collection<Table> getAll();

    @FullManualQuery("get")
    public abstract Table get(String name);

    @FullManualQuery("get_all")
    private Collection<Table> getAllHandler() throws Exception {
        List<Table> result = new ArrayList<>();
        Connection conn = getDatabase().getConnection();
        PreparedStatement st = conn.prepareStatement(
                "select name, sql from sqlite_master " +
                        "where type='table' and " +
                        "name <> 'sqlite_sequence'");
        ResultSet rs = st.executeQuery();
        while(rs.next())
            result.add(getInjectedEntry(rs));
        rs.close();
        conn.close();
        for (Table t: result) {
            Field f = Table.class.getField("columns");
            f.setAccessible(true);
            f.set(t, getDatabase().getColumnManager().getFields(t.getName()));
        }
        return result;
    }

    @QueryHandler("get")
    private Table getHandler(String name) throws Exception {
        Table t = null;
        Connection conn = getDatabase().getConnection();
        PreparedStatement st = conn.prepareStatement(
                "select name, sql from sqlite_master " +
                        "where type='table' and " +
                        "name = ?");
        st.setString(1, name);
        ResultSet rs = st.executeQuery();
        if(rs.next())
            t = getInjectedEntry(rs);
        rs.close();
        conn.close();
        Field f = Table.class.getField("columns");
        f.setAccessible(true);
        f.set(t, getDatabase().getColumnManager().getFields(t.getName()));
        return t;
    }
}
