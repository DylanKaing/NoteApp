import { Component, OnInit, OnDestroy, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import { ActivatedRoute,Router } from '@angular/router';
import { NoteService } from '../shared/services/note-service';
import { Note } from '../shared/models/note';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { FormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { fabric } from 'fabric';

@Component({
  selector: 'app-note-editor',
  imports: [
    CommonModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    FormsModule
  ],
  templateUrl: './note-editor.html',
  styleUrl: './note-editor.css',
})
export class NoteEditor implements OnInit, AfterViewInit, OnDestroy {

  noteName = '';
  noteId: number | null = null;
  note: Note | null = null;
  mode = 'text';
  canvas: fabric.Canvas | null = null;

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

        if(note.content){
          this.canvas?.loadFromJSON(JSON.parse(note.content), () => {
            this.canvas?.renderAll();
          })
        }

      });
    }
  }

  ngOnDestroy(): void {
    this.canvas?.dispose();
    document.removeEventListener('keydown', this.handleKeyDown);
  }

  private handleKeyDown = (e: KeyboardEvent) => {
    if(e.key==='Delete') {
      this.deleteSelected();
    }
  }

  ngAfterViewInit(): void {
    this.canvas = new fabric.Canvas('drawingCanvas', {
        width: window.innerWidth,
        height: 3000,
        isDrawingMode: false
    });
    this.canvas.freeDrawingBrush = new fabric.PencilBrush(this.canvas);

    // click anywhere in text mode to create a text box
    this.canvas.on('mouse:down', (e: any) => {
        if(this.mode === 'text' && !e.target) {
            const text = new fabric.IText('', {
                left: e.pointer?.x,
                top: e.pointer?.y,
                fontSize: 16,
                fontFamily: 'Arial'
            });
            this.canvas?.add(text);
            this.canvas?.setActiveObject(text);
            text.enterEditing();
        }
    });

    this.canvas.on('object:added', (e: any) => {
      if(e.target && e.target.type === 'path') {
          e.target.perPixelTargetFind = true;
          e.target.targetFindTolerance = 5;
      }
    });

    document.addEventListener('keydown', this.handleKeyDown);
  }

  toggleMode() {
    this.mode = this.mode === 'text' ? 'draw' : 'text';
    if(this.canvas) {
        this.canvas.isDrawingMode = this.mode === 'draw';
    }
  }

  selectMode(){
      this.mode = 'select';
      if(this.canvas){
          this.canvas.isDrawingMode = false;
          this.canvas.selection = true;
      }
  }

  deleteSelected() {
    const activeObject = this.canvas?.getActiveObject();
    if(activeObject) {
        this.canvas?.remove(activeObject);
        this.canvas?.discardActiveObject();
        this.canvas?.renderAll();
    }
  }

  save(){ // save entire canvas as JSON
    const content = JSON.stringify(this.canvas?.toJSON());

    if(this.noteId){
      const updatedNote = {...this.note, content:content} as Note;      
      this.noteService.updateNote(this.noteId, updatedNote).subscribe({
        next: () => console.log('Note Saved!'),
        error: (err) => console.error('Save failed', err)
      });
    }
    else {
      const newNote = {
        name: this.noteName,
        content: content,
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
