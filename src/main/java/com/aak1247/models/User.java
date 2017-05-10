package com.aak1247.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.security.MessageDigest;

import static com.sun.org.apache.xerces.internal.impl.dv.util.HexBin.encode;

/**
 * @author aak12 on 2017/5/10.
 */
@Document(collection = "users")
public class User {
    @Id
    private String userId;
    @Indexed
    @NotNull
    private String username;
    @NotNull
    private String password;

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    public void setPassword(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.update(password.getBytes());
            this.password = encode(messageDigest.digest());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean isRightPassword(String pwd){
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.update(pwd.getBytes());
            pwd = encode(messageDigest.digest());
        }catch (Exception e){
            e.printStackTrace();
        }
        return pwd.equals(this.password);
    }
}
