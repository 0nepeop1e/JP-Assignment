package com.shopmart.pops.manager.data.objects;

import com.google.gson.JsonObject;
import com.shopmart.pops.manager.data.abstracts.AbstractEntry;
import com.shopmart.pops.manager.data.annotations.Serialize;
import com.shopmart.pops.manager.data.enums.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.zip.CRC32;

/**
 * A class which represent entries in users table.
 */
public class User extends AbstractEntry {
    @Getter @Serialize
    protected String username = "";
    @Getter @Serialize
    protected String authKey = "";
    @Getter @Setter @Serialize
    protected String staffId = "";
    @Getter @Setter @Serialize
    protected String staffName = "";
    @Serialize
    protected int access = AccessLevel.NoAccess.ordinal();

    public boolean setUsername(String username, String password){
        if(!checkPassword(password)) return false;
        this.username = username;
        this.setPassword(password);
        return true;
    }

    public void setPassword(String password){
        this.authKey = calAuthKey(password);
    }

    public boolean checkPassword(String password){
        return this.authKey.equals(calAuthKey(password));
    }

    public AccessLevel getAccessLevel(){
        return AccessLevel.fromValue(this.access);
    }

    public void setAccessLevel(AccessLevel access){
        this.access = access.ordinal();
    }

    private String calAuthKey(String password){
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            CRC32 crc32 = new CRC32();
            crc32.update(this.username.getBytes());
            Random rand = new Random(crc32.getValue() ^ this.getId());
            byte[] bytes = md.digest(password.getBytes());
            for(byte b:bytes) result +=
                    String.format("%02x", (b ^ rand.nextInt()) & 0xFF);
        } catch (NoSuchAlgorithmException e) {e.printStackTrace();}
        return result;
    }

    protected void setId(int id){
        this.id = id;
    }

    @Override
    public User loadJson(JsonObject json){
        if(json.get("id").getAsInt() == 1)
            return new SuperUser().loadJson(json);
        return super.loadJson(json);
    }
}
