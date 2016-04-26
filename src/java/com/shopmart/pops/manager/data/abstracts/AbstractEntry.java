package com.shopmart.pops.manager.data.abstracts;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.shopmart.pops.manager.data.DataManager;
import com.shopmart.pops.manager.data.annotations.Serialize;
import lombok.Getter;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 0nepeop1e on 4/25/16.
 */
public abstract class AbstractEntry {
    @Getter
    protected int id = 0;
    protected DataManager dataManager = null;

    public JsonObject toJson(){
        JsonObject json = new JsonObject();
        json.addProperty("id", this.id);
        List<Field> fs = Arrays.stream(this.getClass().getDeclaredFields())
                .filter(f->f.getAnnotation(Serialize.class)!=null)
                .collect(Collectors.toList());
        for(Field f:fs){
            f.setAccessible(true);
            try{
                if(String.class.isAssignableFrom(f.getType()))
                    json.addProperty(f.getName(), (String)f.get(this));
                else if (Number.class.isAssignableFrom(f.getType()))
                    json.addProperty(f.getName(), (Number)f.get(this));
                else if (Boolean.class.isAssignableFrom(f.getType()))
                    json.addProperty(f.getName(), f.getBoolean(this));
            }catch (Exception e){e.printStackTrace();}
        }
        return json;
    }
    public <T extends AbstractEntry> T loadJson(JsonObject json){
        List<Field> fs = Arrays.stream(this.getClass().getDeclaredFields())
                .filter(f->f.getAnnotation(Serialize.class)!=null)
                .collect(Collectors.toList());
        for(Field f:fs){
            f.setAccessible(true);
            try{
                if(String.class.isAssignableFrom(f.getType()))
                    f.set(this, json.get(f.getName()).getAsString());
                else if (Integer.class.isAssignableFrom(f.getType()))
                    f.set(this, json.get(f.getName()).getAsInt());
                else if (Double.class.isAssignableFrom(f.getType()))
                    f.set(this, json.get(f.getName()).getAsDouble());
                else if (Boolean.class.isAssignableFrom(f.getType()))
                    f.set(this, json.get(f.getName()).getAsBoolean());
            }catch (Exception e){e.printStackTrace();}
        }
        return (T) this;
    }

    public void setDataManager(DataManager dataManager) {
        if(this.dataManager != null) return;
        this.dataManager = dataManager;
    }
}
