package com.dylan.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.dylan.backend.dto.NoteDTO;

@Entity
@Table(name="notes")
public class Note {

    //Note id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long noteId;    

    // Note Name
    @Column(nullable = false)
    String name;

    // Note data
    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(columnDefinition = "TEXT")
    private String drawingData;

    private String pdfPath;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Dates
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        updatedAt = LocalDateTime.now();
    }

    // ---------------------------------------------------------
    // Constructors
    public Note(NoteDTO noteDTO) {
        this.name = noteDTO.getName();
        this.content = noteDTO.getContent();
        this.drawingData = noteDTO.getDrawingData();
        this.pdfPath = noteDTO.getPdfPath();
    }


    // ---------------------------------------------------------
    // Getters and setters

    public long getNoteId(){
        return noteId;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content = content;
    }

    public String getDrawingData(){
        return drawingData;
    } 

    public void setDrawingData(String drawingData){
        this.drawingData = drawingData;
    }

    public String getPdfPath(){
        return pdfPath;
    }

    public void setPdfPath(String path) {
        this.pdfPath = path;
    }

    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }

    public LocalDateTime getCreatedAt(){
        return createdAt;
    }

    public LocalDateTime getUpdatedAt(){
        return updatedAt;
    }

}
