package com.dylan.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "shared_notes")
public class SharedNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "note_id")
    private Note note;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Permission permission;

    // ---------------------------------------------------------
    // Getters and setters

    public long getId(){
        return id;
    }

    public Note getNote(){
        return note;
    }

    public void setNote(Note note){
        this.note = note;
    }

    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }

    public Permission getPermission(){
        return permission;
    }

    public void setPermission(Permission permission){
        this.permission = permission;
    }
    
}
