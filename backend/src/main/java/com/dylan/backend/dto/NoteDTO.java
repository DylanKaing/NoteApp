package com.dylan.backend.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.cglib.core.Local;

public class NoteDTO {
    private Long noteId;
    private String name;
    private String content;
    private String drawingData;
    private String pdfPath;
    private String ownerUsername; // just the username, not the full User object
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Getters and setters
    public Long getNoteId(){
        return noteId;
    }

    public void setNoteId(Long noteId){
        this.noteId = noteId;
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

    public void setPdfPath(String pdfPath){
        this.pdfPath = pdfPath;
    }

    public String getOwnerUsername(){
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername){
        this.ownerUsername = ownerUsername;
    }

    public LocalDateTime getCreatedAt(){
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt){
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt(){
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt){
        this.updatedAt = updatedAt;
    }

}
