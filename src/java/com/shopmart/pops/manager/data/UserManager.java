package com.shopmart.pops.manager.data;

import com.shopmart.pops.POPSUtils;
import com.shopmart.pops.manager.data.abstracts.AbstractManager;
import com.shopmart.pops.manager.data.enums.AccessLevel;
import com.shopmart.pops.manager.data.objects.User;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * A manager to manage users
 */
public class UserManager extends AbstractManager<User>{

    private int currentUser;

    public User getCurrentUser(){
        return this.getById(currentUser);
    }

    public boolean add(User newUser){
        if(this.getByUsername(newUser.getUsername()) != null)return false;
        return super.add(newUser);
    }

    @Override
    public Class<User> getManageType() {
        return User.class;
    }

    public boolean removeByUsername(String username){
        if(this.getByUsername(username) == null) return false;
        data.remove(this.getByUsername(username));
        return true;
    }

    public boolean login(String username, String password){
        if(this.validate(username, password)){
            currentUser = getByUsername(username).getId();
            return true;
        }
        return false;
    }

    public boolean validate(String username, String password) {
        User u = this.getByUsername(username);
        return u != null && u.checkPassword(password);
    }

    public void logout(){
        currentUser = 0;
    }

    public User getByUsername(String username){
        Optional<User> ou = data.stream().filter(usr->
                usr.getUsername().equals(username)).findFirst();
        return ou.isPresent()?ou.get():null;
    }

    public Collection<User> findByUsername(String glob){
        return data.stream().filter(usr->
                Pattern.matches(POPSUtils.globToRegEx(glob),
                usr.getUsername()) && usr.getId() != 1)
                .collect(Collectors.toList());
    }

    public Collection<User> findByStaffId(String glob){
        return data.stream().filter(usr->
                Pattern.matches(POPSUtils.globToRegEx(glob),
                usr.getStaffId()) && usr.getId() != 1)
                .collect(Collectors.toList());
    }

    public Collection<User> findByStaffName(String glob){
        return data.stream().filter(usr->
                Pattern.matches(POPSUtils.globToRegEx(glob),
                usr.getStaffName()) && usr.getId() != 1)
                .collect(Collectors.toList());
    }

    public Collection<User> findByAccessLevel(String glob){
        return data.stream().filter(usr->
                Pattern.matches(POPSUtils.globToRegEx(glob),
                usr.getAccessLevel().toString()) && usr.getId() != 1)
                .collect(Collectors.toList());
    }

    public Collection<User> findByAccessLevel(AccessLevel access){
        return data.stream().filter(usr->
                usr.getAccessLevel() == access &&
                usr.getId() != 1).collect(Collectors.toList());
    }
}
