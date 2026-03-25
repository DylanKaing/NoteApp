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

    

}
