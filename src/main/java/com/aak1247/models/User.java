package com.aak1247.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.security.MessageDigest;

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
            MessageDigest messageDigest = MessageDigest.getInstance("MD-5");
            messageDigest.update(password.getBytes());
        }catch (Exception e){
            e.printStackTrace();
        }
        this.password = password;
    }

    public boolean isRightPassword(String pwd){
        return pwd.equalsIgnoreCase(this.toString());
    }
}
