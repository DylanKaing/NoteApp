package com.dylan.backend.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.dylan.backend.dto.NoteDTO;
import com.dylan.backend.service.NoteService;
import com.dylan.backend.entity.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping("/user/{userId}")
    public ResponseEntity<NoteDTO> createNote(@RequestBody NoteDTO newNote, @PathVariable Long userId){
        return ResponseEntity.ok(noteService.createNote(newNote, userId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NoteDTO>> getNotesByUser(@PathVariable Long userId){
        return ResponseEntity.ok(noteService.getNotesByUser(userId));
    }

    @GetMapping("/{noteId}")
    public ResponseEntity<NoteDTO> getNoteById(@PathVariable Long noteId){
        return ResponseEntity.ok(noteService.getNoteById(noteId));
    }

    @PutMapping("/{noteId}")
    public ResponseEntity<NoteDTO> updateNote(@RequestBody NoteDTO noteDTO, @PathVariable Long noteId){
        return ResponseEntity.ok(noteService.updateNote(noteId, noteDTO));
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long noteId){
        noteService.deleteNote(noteId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{noteId}/share/{userId}")
    public ResponseEntity<Void> shareNote(@PathVariable Long noteId, @PathVariable Long userId, @RequestBody Permission permission){
        noteService.shareNote(noteId,userId,permission);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/shared/{userId}")
    public ResponseEntity<List<NoteDTO>> getSharedNotes(@PathVariable Long userId){
        return ResponseEntity.ok(noteService.getSharedNotes(userId));
    }
    
}
