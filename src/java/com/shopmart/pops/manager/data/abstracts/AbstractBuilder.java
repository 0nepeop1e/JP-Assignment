package com.shopmart.pops.manager.data.abstracts;

/**
 * Created by 0nepeop1e on 4/26/16.
 */
public abstract class AbstractBuilder<T extends AbstractEntry> {
    protected T data;
    public T addToManager(AbstractManager<T> manager){
        if(manager.add(data)) return data;
        return null;
    }
}
