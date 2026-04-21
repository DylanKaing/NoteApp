package com.dylan.backend.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.cglib.core.Local;

import com.dylan.backend.entity.Note;

public class NoteDTO {
    private Long noteId;
    private String name;
    private String content;
    private String drawingData;
    private String pdfPath;
    private String ownerUsername; // just the username, not the full User object
    private Long ownerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public NoteDTO(Note note) {
        this.noteId = note.getNoteId();
        this.name = note.getName();
        this.content = note.getContent();
        this.drawingData = note.getDrawingData();
        this.pdfPath = note.getPdfPath();
        this.ownerUsername = note.getUser().getUsername();
        this.ownerId = note.getUser().getUserId();
        this.createdAt = note.getCreatedAt();
        this.updatedAt = note.getUpdatedAt();
    }

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

    public Long getOwnerId(){
        return ownerId;
    }

    public void setOwnerId(Long ownerId){
        this.ownerId = ownerId;
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
