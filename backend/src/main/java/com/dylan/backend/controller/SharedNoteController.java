package com.dylan.backend.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.dylan.backend.service.SharedNoteService;
import com.dylan.backend.entity.Permission;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/shared")
public class SharedNoteController {
    
    @Autowired
    private SharedNoteService sharedNoteService;

    @DeleteMapping("/{noteId}/revoke/{userId}")
    public ResponseEntity<Void> revokeAccess(@PathVariable Long noteId, @PathVariable Long userId){
        sharedNoteService.revokeAccess(noteId, userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{noteId}/permission/{userId}")
    public ResponseEntity<Void> updatePermission(@PathVariable Long noteId, @PathVariable Long userId, @RequestBody Permission permission){
        sharedNoteService.updatePermission(noteId, userId, permission);
        return ResponseEntity.noContent().build();
    }

}
