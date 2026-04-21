import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { Router } from '@angular/router';
import { RouterLink } from '@angular/router';
import { NoteService } from '../shared/services/note-service';
import { Note } from '../shared/models/note';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-dashboard',
  imports: [
    MatButtonModule,
    MatCardModule,
    CommonModule,
    RouterLink
  ],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard implements OnInit{

  notes: Note[] = [];

  filter = 'all';

    //services
  constructor(
    private noteService: NoteService, 
    private router: Router
  ){}


  ngOnInit(): void {
    const userId = Number(localStorage.getItem('userId'));
    this.noteService.getNotesByUser(userId).subscribe(notes => {
      this.notes = notes;
    });

    this.noteService.getSharedNotes(userId).subscribe(sharedNotes=>{
      this.notes = [...this.notes, ...sharedNotes];
    });
  }

  deleteNote(noteId: number) {
    this.noteService.deleteNote(noteId).subscribe({
      next: () => {
        this.notes = this.notes.filter(note => note.noteId !== noteId);
      },
      error: (err) => {
        console.error('Delete failed', err);
      }
    });
  }

  openNote(noteId: number) {
    this.router.navigate(['/note-editor', noteId]);
  }

  get filteredNotes(): Note[] {
    if(this.filter==='mine'){
      const userId = Number(localStorage.getItem('userId'));
      return this.notes.filter(note => note.ownerId === userId);
    }
    if(this.filter==='shared'){
      const userId = Number(localStorage.getItem('userId'));
      return this.notes.filter(note => note.ownerId !== userId);
    }
    return this.notes; // all notes
  }
}
