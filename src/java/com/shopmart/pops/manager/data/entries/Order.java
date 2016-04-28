package com.shopmart.pops.manager.data.entries;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.shopmart.pops.POPS;
import com.shopmart.pops.manager.data.abstracts.AbstractEntry;
import com.shopmart.pops.manager.data.annotations.Serialize;
import com.shopmart.pops.manager.data.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 0nepeop1e on 4/26/16.
 */
public class Order extends AbstractEntry {
    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    @Getter  @Setter
    private LocalDateTime time = LocalDateTime.now();
    @Setter @Serialize
    private int supplier;
    @Getter
    private Map<Integer, Integer> itemsAmount = new HashMap<>();
    @Setter @Serialize
    private int creator = 0;
    @Serialize
    private int status = 0;

    public Supplier getSupplier(){
        return POPS.getDataManager().getSupplierManager().getById(supplier);
    }

    public User getCreator(){
        return POPS.getDataManager().getUserManager().getById(creator);
    }

    public OrderStatus getStatus(){
        return OrderStatus.fromValue(status);
    }

    public void setStatus(OrderStatus status){
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
    public Order loadJson(JsonObject json){
        super.loadJson(json);
        this.time = LocalDateTime.parse(json.get("time").getAsString(), format);
        Gson gson = new GsonBuilder().create();
        Type token = new TypeToken<Map<Integer, Integer>>(){}.getType();
        this.itemsAmount = Collections.unmodifiableMap(
                gson.fromJson(json.get("items") ,token));
        return this;
    }
}
