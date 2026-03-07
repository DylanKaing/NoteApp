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

    private String permission;
    
}
