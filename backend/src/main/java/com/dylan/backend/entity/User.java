package com.dylan.backend.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    
    // UserId
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    // Username
    @Column(nullable = false, unique = true)
    private String username;

    // Password
    @Column(nullable = false)
    private String password;

    // Email
    @Column(nullable = false, unique = true)
    private String email;

    // Notes linked to the user
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Note> notes;

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

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public List<Note> getNotes(){
        return notes;
    }

    public void setNotes(List<Note> notes){
        this.notes = notes;
    }

}
