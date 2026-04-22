package com.dylan.backend.service;

import com.dylan.backend.dto.NoteDTO;
import com.dylan.backend.entity.User;
import com.dylan.backend.entity.Note;
import com.dylan.backend.entity.SharedNote;
import com.dylan.backend.entity.Permission;
import com.dylan.backend.repository.NoteRepository;
import com.dylan.backend.repository.SharedNoteRepository;
import com.dylan.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service
public class NoteService {
    
    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SharedNoteRepository sharedNoteRepository;

    //createNote
    public NoteDTO createNote(NoteDTO note, Long userId){
        //find user by id, if user does not exist then throw exception
        User user = userRepository.findById(userId).orElseThrow(() ->
            new RuntimeException("User not found"));

        //make new note obj
        Note newNote = new Note(note);
        newNote.setUser(user);

        //save the note
        noteRepository.save(newNote);

        //return saved noteDTO
        return new NoteDTO(newNote);
    }

    //getNotesByUser
    public List<NoteDTO> getNotesByUser(Long userId){
        //find user by id and throw exception if they do not exist
        User user = userRepository.findById(userId).orElseThrow(() ->
            new RuntimeException("User not found"));

        //find list of notes connected to userId
        List<Note> notes = noteRepository.findByUserUserId(user.getUserId());

        //convert all notes the to notedto
        List<NoteDTO> noteDTOs = new ArrayList<NoteDTO>();
        for(Note note : notes){
            noteDTOs.add(new NoteDTO(note));
        }

        return noteDTOs;
    }

    //getNoteById
    public NoteDTO getNoteById(Long noteId){
        Note note = noteRepository.findById(noteId).orElseThrow(() ->
            new RuntimeException("Note not found"));

        return new NoteDTO(note);
    }

    //updateNote
    public NoteDTO updateNote(Long noteId, NoteDTO noteDto){
        Note note = noteRepository.findById(noteId).orElseThrow(() ->
            new RuntimeException("Note not found"));

        // Update note's name, content, and pdfPath
        note.setName(noteDto.getName());
        note.setContent(noteDto.getContent());
        note.setPdfPath(noteDto.getPdfPath());

        // Save updated note and then return it
        noteRepository.save(note);

        return new NoteDTO(note);
    }

    //deleteNote
    public void deleteNote(Long noteId){
        Note note = noteRepository.findById(noteId).orElseThrow(() ->
            new RuntimeException("Note not found"));
        
        noteRepository.deleteById(note.getNoteId());
    }

    //shareNote
    public void shareNote(Long noteId, Long userId, Permission permission){
        Note note = noteRepository.findById(noteId).orElseThrow(() ->
            new RuntimeException("Note not found"));

        User user = userRepository.findById(userId).orElseThrow(() ->
            new RuntimeException("User not found"));

        SharedNote sNote = new SharedNote(note, user, permission);

        sharedNoteRepository.save(sNote);
    }

    //getSharedNotes
    public List<NoteDTO> getSharedNotes(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() ->
            new RuntimeException("User not found"));

        List<SharedNote> sNotes = sharedNoteRepository.findByUserUserId(user.getUserId());

        List<NoteDTO> notes = new ArrayList<>();
        for(SharedNote sNote : sNotes){
            notes.add(new NoteDTO(sNote.getNote()));
        }

        return notes;
    }

}
