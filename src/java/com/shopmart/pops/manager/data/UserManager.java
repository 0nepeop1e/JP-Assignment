package com.shopmart.pops.manager.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.shopmart.pops.POPSUtils;
import com.shopmart.pops.manager.data.objects.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class UserManager {

    private List<User> users;
    private int lastId;
    private int currentUser;

    public UserManager(){
        this.users = new ArrayList<>();
        this.lastId = 0;
    }

    public User getCurrentUser(){
        return this.getById(currentUser);
    }

    public boolean login(String username, String password){
        User u = this.getByUsername(username);
        if(u == null) return false;
        if(!u.checkPassword(password))return false;
        currentUser = u.getId();
        return true;
    }

    public void logout(){
        currentUser = 0;
    }

    public User getById(int id){
        Optional<User> ou = users.stream().filter(usr->
                usr.getId() == id).findFirst();
        return ou.isPresent()?ou.get():null;
    }

    public User getByUsername(String username){
        Optional<User> ou = users.stream().filter(usr->
                usr.getUsername().equals(username)).findFirst();
        return ou.isPresent()?ou.get():null;
    }

    public User getByStaffId(String staffId){
        Optional<User> ou = users.stream().filter(usr->
                usr.getStaffId().equals(staffId)).findFirst();
        return ou.isPresent()?ou.get():null;
    }

    public Collection<User> findByUsername(String glob){
        return users.stream().filter(usr->
                Pattern.matches(POPSUtils.globToRegEx(glob),
                usr.getUsername())).collect(Collectors.toList());
    }

    public Collection<User> findByStaffId(String glob){
        return users.stream().filter(usr->
                Pattern.matches(POPSUtils.globToRegEx(glob),
                usr.getStaffId())).collect(Collectors.toList());
    }

    public Collection<User> findByStaffName(String glob){
        return users.stream().filter(usr->
                Pattern.matches(POPSUtils.globToRegEx(glob),
                usr.getStaffName())).collect(Collectors.toList());
    }

    public Collection<User> findByGroup(int groupId){
        return users.stream().filter(usr->
               usr.getGroupId() == groupId).collect(Collectors.toList());
    }

    public JsonObject toJson(){
        JsonObject jo = new JsonObject();
        JsonArray ja = new JsonArray();
        jo.addProperty("last_id", lastId);
        for (User u:users) ja.add(u.toJson());
        jo.add("data", ja);
        return jo;
    }

    public static UserManager fromJson(JsonObject json){
        UserManager um = new UserManager();
        um.lastId = json.get("last_id").getAsInt();
        for(JsonElement je : json.get("data").getAsJsonArray())
            um.users.add(User.fromJson(je.getAsJsonObject()));
        return um;
    }
}
