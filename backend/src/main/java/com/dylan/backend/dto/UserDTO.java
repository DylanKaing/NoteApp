package com.dylan.backend.dto;

public class UserDTO {

    private long userId;
    private String username;
    private String email;

    // ---------------------------------------------------------
    // Getters and setters

    public long getUserId(){
        return userId;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }
}
