package com.shopmart.pops.manager.data.objects;

import com.google.gson.*;
import lombok.Getter;
import lombok.Setter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.zip.CRC32;

/**
 * A class which represent entries in users table.
 */
public class User {
    @Getter
    private int id;
    @Getter
    private String username;
    @Getter
    private String authKey;
    @Getter @Setter
    private String staffId;
    @Getter @Setter
    private String staffName;
    @Getter @Setter
    private int groupId;

    public void changePassword(String password){
        this.authKey = calAuthKey(password);
    }

    public boolean checkPassword(String password){
        return this.authKey.equals(calAuthKey(password));
    }

    public JsonObject toJson() {
        JsonObject jo = new JsonObject();
        jo.addProperty("id", this.id);
        jo.addProperty("user", this.username);
        jo.addProperty("auth", this.authKey);
        jo.addProperty("staff", this.staffId);
        jo.addProperty("name", this.staffName);
        jo.addProperty("group", this.groupId);
        return jo;
    }

    private String calAuthKey(String password){
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            CRC32 crc32 = new CRC32();
            crc32.update(this.username.getBytes());
            Random rand = new Random(crc32.getValue() ^ this.id);
            byte[] bytes = md.digest(password.getBytes());
            for(byte b:bytes) result +=
                    String.format("%02x", b ^ (rand.nextInt() % 256));
        } catch (NoSuchAlgorithmException e) {e.printStackTrace();}
        return result;
    }

    public static User fromJson(JsonObject jo){
        User u = new User();
        u.id = jo.get("id").getAsInt();
        u.username = jo.get("user").getAsString();
        u.authKey = jo.get("auth").getAsString();
        u.staffId = jo.get("staff").getAsString();
        u.staffName = jo.get("name").getAsString();
        u.groupId = jo.get("group").getAsInt();
        return u;
    }
}
