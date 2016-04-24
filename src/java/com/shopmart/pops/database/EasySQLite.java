package com.shopmart.pops.database;

import com.shopmart.pops.database.annotations.AutoQuery;
import com.shopmart.pops.database.annotations.FullManualQuery;
import com.shopmart.pops.database.annotations.SemiManualQuery;
import com.shopmart.pops.database.annotations.TableEntry;
import com.shopmart.pops.database.entities.Entry;
import com.shopmart.pops.database.manager.ColumnManager;
import com.shopmart.pops.database.manager.DataManager;
import com.shopmart.pops.database.manager.TableManager;
import javassist.scopedpool.SoftValueHashMap;
import javassist.util.proxy.Proxy;
import javassist.util.proxy.ProxyFactory;
import lombok.Getter;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * A class for easy to manage sqlite database.
 */
public class EasySQLite {
    @Getter
    private String path;
    @Getter
    private TableManager tableManager;
    @Getter
    private ColumnManager columnManager;
    private Map<Class<?>, DataManager<?>> managers;
    public EasySQLite(String dbFile){
        managers = new HashMap<>();
        tableManager = getManager(TableManager.class);
        columnManager = getManager(ColumnManager.class);
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        this.path = dbFile;
    }

    public Connection getConnection(){
        try {
            return DriverManager.getConnection(String.format("jdbc:sqlite:%s",this.path));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T extends DataManager> T getManager(Class<T> managerClass){
        if(managers.containsKey(managerClass)) return (T) managers.get(managerClass);
        ProxyFactory factory = new ProxyFactory();
        Class<? extends Entry> enClass =
                (Class<? extends Entry>) ((ParameterizedType)managerClass
                        .getGenericInterfaces()[0])
                        .getActualTypeArguments()[0];
        factory.setSuperclass(managerClass);
        factory.setFilter((m)-> m.getAnnotation(AutoQuery.class) != null ||
                        m.getAnnotation(SemiManualQuery.class) != null ||
                        m.getAnnotation(FullManualQuery.class) != null ||
                        m.getName().equals("getInjectedEntry"));
        Proxy p = null;
        try {
            p = (Proxy) factory.create(new Class[0], new Object[0]);
            p.setHandler((self, m, proceed, args)->{
                if(m.getName().equals("getInjectedEntry")){
                    getInjectedEntry(enClass, (ResultSet) args[0]);
                }
                switch(m.getName()){
                    case "insert":
                        insert((Entry) args[0]);
                        return null;
                    case "update":
                        update((Entry) args[0]);
                        return null;
                    case "delete":
                        delete((Entry) args[0]);
                        return null;
                    case "count":
                        return count(getTableName(enClass));
                    case "getAll":
                        return getList(enClass);
                    case "getDatabase":
                        return EasySQLite.this;
                }
                return null;
            });
        } catch (Exception e){
            e.printStackTrace();
        }
        managers.put(managerClass, (DataManager<?>) p);
        return (T) p;
    }

    private <T extends Entry> T getInjectedEntry(Class<? extends Entry> enClass, ResultSet rs) throws Exception {
        Method inject = enClass.getMethod("inject", ResultSet.class);
        inject.setAccessible(true);
        return (T)inject.invoke(enClass.newInstance(), rs);
    }

    private Collection<? extends Entry> getList(Class<? extends Entry> enClass) throws Exception {
        ArrayList<? extends Entry> list = new ArrayList<>();
        String sql = String.format("SELECT * FROM `%s`", getTableName(enClass));
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            list.add(getInjectedEntry(enClass, rs));
        }
        return list;
    }

    private int count(String tableName) {
        return 0;
    }

    private void delete(Entry en) {
        String table = getTableName(en.getClass());
    }

    private void update(Entry en) {
        String table = getTableName(en.getClass());
    }

    private void insert(Entry en) {
        String table = getTableName(en.getClass());
    }

    private String getTableName(Class<? extends Entry> kelas){
        return kelas.getAnnotation(TableEntry.class).value();
    }
}