package com.shopmart.pops.manager.data.abstracts;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.shopmart.pops.manager.data.DataManager;
import lombok.Getter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by 0nepeop1e on 4/25/16.
 */
public abstract class AbstractManager<T extends AbstractEntry> {
    protected List<T> data;
    protected int lastId;
    @Getter
    protected DataManager dataManager;

    public AbstractManager(){
        this.data = new ArrayList<>();
        this.lastId = 0;
        this.dataManager = null;
    }

    public boolean add(T entry){
        try {
            Field f = AbstractEntry.class.getDeclaredField("id");
            f.setAccessible(true);
            f.set(entry, ++this.lastId);
            entry.setDataManager(this.dataManager);
            data.add(entry);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeById(int id){
        if(this.getById(id) == null) return false;
        this.data.remove(this.getById(id));
        return true;
    }

    public T getById(int id){
        Optional<T> ou = this.data.stream().filter(usr->
                usr.getId() == id).findFirst();
        return ou.isPresent()?ou.get():null;
    }

    public JsonObject toJson(){
        JsonObject jo = new JsonObject();
        JsonArray ja = new JsonArray();
        jo.addProperty("last_id", lastId);
        for (T e:data) ja.add(e.toJson());
        jo.add("data", ja);
        return jo;
    }
    public <E extends AbstractManager<T>> E loadJson(JsonObject json){
        this.data.clear();
        this.lastId = json.get("last_id").getAsInt();
        for(JsonElement el : json.get("data").getAsJsonArray())
            try {
                this.data.add(getManageType().newInstance()
                        .loadJson(el.getAsJsonObject()));
            } catch (Exception e) {e.printStackTrace();}
        return (E) this;
    }

    public void setDataManager(DataManager dataManager) {
        if(this.dataManager != null) return;
        this.dataManager = dataManager;
    }

    public abstract Class<T> getManageType();
}
