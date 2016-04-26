package com.shopmart.pops.manager.data;

import com.google.gson.JsonObject;
import com.shopmart.pops.manager.data.enums.AccessLevel;
import com.shopmart.pops.manager.data.objects.Request;
import com.shopmart.pops.manager.data.objects.SuperUser;
import com.shopmart.pops.manager.data.objects.User;
import com.shopmart.pops.manager.data.objects.builder.UserBuilder;
import lombok.Getter;

import java.util.Random;

/**
 * A manager to manage managers
 */
public class DataManager {
    @Getter
    UserManager userManager;
    @Getter
    ItemManager itemManager;
    @Getter
    SupplierManager supplierManager;
    @Getter
    RequestManager requestManager;

    public DataManager(){
        this.userManager = new UserManager();
        this.itemManager = new ItemManager();
        this.supplierManager = new SupplierManager();
        this.userManager.setDataManager(this);
        this.itemManager.setDataManager(this);
        this.supplierManager.setDataManager(this);
        SuperUser su = new SuperUser();
        String key = "";
        su.setPassword("admin");
        this.userManager.add(su);
    }

    public JsonObject toJson(){
        JsonObject jo = new JsonObject();
        jo.add("users", this.userManager.toJson());
        jo.add("items", this.itemManager.toJson());
        jo.add("suppliers", this.supplierManager.toJson());
        jo.add("requests", this.requestManager.toJson());
        return jo;
    }

    public DataManager loadJson(JsonObject json){
        this.userManager = new UserManager().loadJson(
                json.get("users").getAsJsonObject());
        this.itemManager = new ItemManager().loadJson(
                json.get("items").getAsJsonObject());
        this.supplierManager = new SupplierManager().loadJson(
                json.get("suppliers").getAsJsonObject());
        this.requestManager = new RequestManager().loadJson(
                json.get("requests").getAsJsonObject());
        return this;
    }
}
