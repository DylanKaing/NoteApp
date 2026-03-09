package com.dylan.backend.dto;

import java.time.LocalDateTime;

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
}
