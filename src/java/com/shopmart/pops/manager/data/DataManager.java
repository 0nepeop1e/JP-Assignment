package com.shopmart.pops.manager.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.shopmart.pops.manager.data.entries.SuperUser;
import lombok.Getter;

import java.io.*;
import java.util.stream.Collectors;

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
    @Getter
    OrderManager orderManager;

    public DataManager(){
        this.userManager = new UserManager();
        this.itemManager = new ItemManager();
        this.supplierManager = new SupplierManager();
        this.requestManager = new RequestManager();
        this.orderManager = new OrderManager();
        this.userManager.setDataManager(this);
        this.itemManager.setDataManager(this);
        this.supplierManager.setDataManager(this);
        this.requestManager.setDataManager(this);
        this.orderManager.setDataManager(this);
        SuperUser su = new SuperUser();
        su.setPassword("admin");
        this.userManager.add(su);
    }

    public void saveTo(String path){
        try {
            FileWriter fw = new FileWriter(path, false);
            Gson gson = new GsonBuilder().create();
            fw.write(gson.toJson(this.toJson()));
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static DataManager loadFrom(String path){
        try{
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            String jstr = String.join(System.lineSeparator(),
                    br.lines().collect(Collectors.toList()));
            br.close();
            return new DataManager().loadJson(new JsonParser()
                    .parse(jstr).getAsJsonObject());
        }catch (IOException e){
            e.printStackTrace();
        }
        return new DataManager();
    }

    public JsonObject toJson(){
        JsonObject jo = new JsonObject();
        jo.add("users", this.userManager.toJson());
        jo.add("items", this.itemManager.toJson());
        jo.add("suppliers", this.supplierManager.toJson());
        jo.add("requests", this.requestManager.toJson());
        jo.add("orders", this.orderManager.toJson());
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
        this.orderManager = new OrderManager().loadJson(
                json.get("orders").getAsJsonObject());
        return this;
    }
}
