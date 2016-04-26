package com.shopmart.pops.manager.data.objects.builder;

import com.shopmart.pops.manager.data.abstracts.AbstractBuilder;
import com.shopmart.pops.manager.data.abstracts.AbstractManager;
import com.shopmart.pops.manager.data.enums.AccessLevel;
import com.shopmart.pops.manager.data.objects.User;

import java.lang.reflect.Field;

/**
 * Created by 0nepeop1e on 4/26/16.
 */
public class UserBuilder extends AbstractBuilder<User>{
    private String password;
    public UserBuilder(){
        this.data = new User();
    }

    public UserBuilder setUsername(String username){
        try {
            Field f = User.class.getDeclaredField("username");
            f.setAccessible(true);
            f.set(this.data, username);
        } catch (Exception e) {e.printStackTrace();}
        return this;
    }

    public UserBuilder setPassword(String password){
        this.password = password;
        return this;
    }

    public UserBuilder setStaffId(String staffId){
        this.data.setStaffId(staffId);
        return this;
    }

    public UserBuilder setStaffName(String staffName){
        this.data.setStaffName(staffName);
        return this;
    }

    public UserBuilder setAccessLevel(AccessLevel accessLevel){
        this.data.setAccessLevel(accessLevel);
        return this;
    }

    @Override
    public User addToManager(AbstractManager<User> manager){
        if(super.addToManager(manager) != null){
            this.data.setPassword(this.password);
            return this.data;
        }
        return null;
    }
}
