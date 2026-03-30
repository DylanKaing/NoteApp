package com.dylan.backend.service;

import com.dylan.backend.entity.Note;
import com.dylan.backend.entity.User;
import com.dylan.backend.entity.SharedNote;
import com.dylan.backend.entity.Permission;
import com.dylan.backend.repository.SharedNoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SharedNoteService {
    
    @Autowired
    private SharedNoteRepository sharedNoteRepository;

    public void revokeAccess(Long noteId, Long userId){
        SharedNote sNote = sharedNoteRepository.findByNoteNoteIdAndUserUserId(noteId, userId);
        if(sNote == null){
            throw new RuntimeException("Shared note not found");
        }
        sharedNoteRepository.delete(sNote);
    }

    public void updatePermission(Long noteId, Long userId, Permission permission){
        SharedNote sNote = sharedNoteRepository.findByNoteNoteIdAndUserUserId(noteId, userId);
        if(sNote == null){
            throw new RuntimeException("Shared note not found");
        }
        sNote.setPermission(permission);
        sharedNoteRepository.save(sNote);
    }

}
