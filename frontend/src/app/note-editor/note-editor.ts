import { Component, OnInit, OnDestroy, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import { ActivatedRoute,Router } from '@angular/router';
import { NoteService } from '../shared/services/note-service';
import { Note } from '../shared/models/note';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { Editor } from '@tiptap/core';
import StarterKit from '@tiptap/starter-kit';
import * as fabric from 'fabric';

@Component({
  selector: 'app-note-editor',
  imports: [
    CommonModule,
    MatButtonModule
  ],
  templateUrl: './note-editor.html',
  styleUrl: './note-editor.css',
})
export class NoteEditor implements OnInit, AfterViewInit, OnDestroy {

  noteName = '';
  noteId: number | null = null;
  note: Note | null = null;
  mode = 'text';
  editor: Editor | null = null;
  canvas: fabric.Canvas | null = null;

  @ViewChild('editorContainer') editorContainer!: ElementRef;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private noteService: NoteService
  ){}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if(id && id !=='new'){
      this.noteId = Number(id);
      this.noteService.getNoteById(this.noteId).subscribe(note =>{
        this.note = note;

        this.editor?.commands.setContent(note.content || '');
      });
    }
  }

  ngOnDestroy(): void {
    this.editor?.destroy();
    this.canvas?.dispose();
  }

  ngAfterViewInit(): void {
    this.editor = new Editor({
      element: this.editorContainer.nativeElement,
      extensions:[StarterKit],
      content: this.note?.content || '',
    });

    this.canvas = new fabric.Canvas('drawingCanvas', {
      isDrawingMode: false
    });
  }

  toggleMode(){
    this.mode = this.mode === 'text' ? 'draw' : 'text';
    if(this.canvas){
      this.canvas.isDrawingMode = this.mode ==='draw';
    }
  }

  save(){
    const content = this.editor?.getHTML();

    if(this.noteId){
      const updatedNote = {...this.note, content:content} as Note;      
      this.noteService.updateNote(this.noteId, updatedNote).subscribe({
        next: () => {
          console.log('Note Saved!');
        },
        error: (err) => {
          console.error('Save failed', err);
        }
      });
    }
    else {
      const newNote = {
        name: this.noteName,
        content: content,
        drawingData: '',
        pdfPath: ''
      } as Note;
      const userId = Number(localStorage.getItem('userId'));
      this.noteService.createNote(newNote, userId).subscribe({
        next: (createdNote) => {
          this.noteId = createdNote.noteId;
          console.log('Note Saved!');
        },
        error: (err) => {
          console.error('Save failed', err);
        }
      });
    }
  }

}
