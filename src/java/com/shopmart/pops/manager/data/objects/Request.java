package com.shopmart.pops.manager.data.objects;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.shopmart.pops.manager.data.abstracts.AbstractEntry;
import com.shopmart.pops.manager.data.annotations.Serialize;
import com.shopmart.pops.manager.data.enums.RequestStatus;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class Request extends AbstractEntry {
    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    @Getter @Setter
    private LocalDate time;
    @Getter
    private Map<Integer, Integer> itemsAmount;
    @Setter @Serialize
    private int creator;
    @Serialize
    private int status;
    @Setter @Serialize
    private int modifier;

    public User getCreator(){
        return this.dataManager.getUserManager()
                .getById(creator);
    }

    public User getModifier(){
        return this.dataManager.getUserManager()
                .getById(modifier);
    }

    public RequestStatus getStatus(){
        return RequestStatus.fromValue(status);
    }

    public void setStatus(RequestStatus status){
        this.status = status.ordinal();
    }

    @Override
    public JsonObject toJson(){
        JsonObject json = super.toJson();
        json.addProperty("time", format.format(time));
        Gson gson = new GsonBuilder().create();
        json.add("items", new JsonParser()
                .parse(gson.toJson(itemsAmount)));
        return json;
    }

    @Override
    public Request loadJson(JsonObject json){
        super.loadJson(json);
        this.time = LocalDate.parse(json.get("data").getAsString(), format);
        Gson gson = new GsonBuilder().create();
        Type token = new TypeToken<Map<Integer, Integer>>(){}.getType();
        this.itemsAmount = gson.fromJson(json.get("items") ,token);
        return this;
    }
}
