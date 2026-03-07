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
    @ManyToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Note> notes;

    

    
}
