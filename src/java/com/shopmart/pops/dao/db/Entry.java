package com.shopmart.pops.dao.db;

import com.shopmart.pops.dao.annotations.EntityFromTable;
import com.shopmart.pops.dao.annotations.AssignWithField;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Entry of an entity.
 */
public abstract class Entry {
    private static Map<Class<? extends Entry>, Map<Object, WeakReference<Entry>>> instances = new HashMap<>();

    /**
     * Cache the reference of an entity.
     * @param en the entity to cache
     */
    private static void cacheReference(Entry en){
        Class<? extends Entry> kelas = en.getClass();
        if(instances.getOrDefault(kelas, null) == null)
            instances.put(kelas, new HashMap<>());
        EntityFromTable notation = en.getClass().getAnnotation(EntityFromTable.class);
        for (Field f: en.getClass().getFields()) {
            AssignWithField ef;
            if ((ef = f.getAnnotation(AssignWithField.class)) != null){
                String fieldname = ef.value().isEmpty() ?
                        f.getName() : ef.value();

            }
        }
    }

    /**
     * Try get a instance from the reference cache.
     * @param kelas Class of the entry
     * @param key primary key of the entry
     * @return the existing instance.
     */
    static Entry tryFindInstance(Class<? extends Entry> kelas, Object key){
        Map<Object, WeakReference<Entry>> map;
        if((map = instances.get(kelas))==null) return null;
        WeakReference<Entry> entry;
        if((entry = map.get(key)) == null) return null;
        if(entry.isEnqueued()) return null;
        return entry.get();
    }

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
     * Cache the reference of this instance.
     */
    void cacheThis(){
        Entry.cacheReference(this);
    }
}
