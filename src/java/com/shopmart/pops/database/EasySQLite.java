package com.shopmart.pops.database;

import com.shopmart.pops.database.annotations.AutoQuery;
import com.shopmart.pops.database.annotations.FullManualQuery;
import com.shopmart.pops.database.annotations.SemiManualQuery;
import com.shopmart.pops.database.annotations.TableEntry;
import com.shopmart.pops.database.entities.Entry;
import com.shopmart.pops.database.manager.DataManager;
import com.shopmart.pops.database.manager.TableManager;
import javassist.util.proxy.Proxy;
import javassist.util.proxy.ProxyFactory;
import lombok.Getter;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A class for easy to manage sqlite database.
 */
public class EasySQLite {
    @Getter
    private String path;
    @Getter
    private TableManager tableManager;
    public EasySQLite(String dbFile){
        tableManager = getManager(TableManager.class);
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        this.path = dbFile;
    }

    Connection getConnection(){
        try {
            return DriverManager.getConnection(String.format("jdbc:sqlite:%s",this.path));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T extends DataManager> T getManager(Class<T> managerClass){
        ProxyFactory factory = new ProxyFactory();
        Class<? extends Entry> enClass =
                (Class<? extends Entry>) ((ParameterizedType)managerClass
                        .getGenericSuperclass())
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
                    Method inject = enClass.getMethod("inject", ResultSet.class);
                    return inject.invoke(enClass.newInstance(), args[0]);
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
                        if(args.length > 0 )
                            return count(getTableName(enClass), (Criteria) args[0]);
                        else return count(getTableName(enClass));
                }
                return null;
            });
        } catch (Exception e){
            e.printStackTrace();
        }
        return (T) p;
    }

    private int count(String tableName) {
        return 0;
    }

    private int count(String tableName, Criteria arg) {
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