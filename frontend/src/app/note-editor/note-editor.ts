import { Component, OnInit, OnDestroy, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import { ActivatedRoute,Router, RouterLink } from '@angular/router';
import { NoteService } from '../shared/services/note-service';
import { Note } from '../shared/models/note';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { FormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { fabric } from 'fabric';
import { ChangeDetectorRef } from '@angular/core';

import * as pdfjsLib from 'pdfjs-dist';
import { jsPDF } from 'jspdf';

pdfjsLib.GlobalWorkerOptions.workerSrc = new URL(
    'pdfjs-dist/build/pdf.worker.mjs',
    import.meta.url
).toString();


@Component({
  selector: 'app-note-editor',
  imports: [
    CommonModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    RouterLink
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
  pdfBase64: string = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private noteService: NoteService,
    private cdr: ChangeDetectorRef
  ){}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if(id && id !=='new'){
      this.noteId = Number(id);
      this.noteService.getNoteById(this.noteId).subscribe(note =>{
        this.note = note;

        if(note.name) {
          this.noteName = note.name;
        }

        if(note.pdfPath) {
          this.pdfBase64 = note.pdfPath;
          this.renderPdf(note.pdfPath);
        }

        if(note.content){
          this.canvas?.loadFromJSON(JSON.parse(note.content), () => {
            this.canvas?.renderAll();
          })
        }

        this.cdr.detectChanges();

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
        width: 918,
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

  onPdfUpload(event: any) {
    const file = event.target.files[0];
    if (!file) return;

    const reader = new FileReader();
    reader.onload = (e: any) => {
        const base64 = e.target.result;
        this.pdfBase64 = base64;
        this.renderPdf(base64);
    };
    reader.readAsDataURL(file);
}

  async renderPdf(base64: string) {
    const loadingTask = pdfjsLib.getDocument(base64);
    const pdf = await loadingTask.promise;
    const page = await pdf.getPage(1);
    
    // increase scale for better quality
    // const scale = window.devicePixelRatio*2;
    // const scale = window.devicePixelRatio*1.2;
    const scale = 1.5;
    const viewport = page.getViewport({ scale });
    
    const tempCanvas = document.createElement('canvas');
    tempCanvas.width = viewport.width;
    tempCanvas.height = viewport.height;
    const ctx = tempCanvas.getContext('2d')!;
    
    await page.render({
        canvasContext: ctx,
        viewport: viewport,
        canvas: tempCanvas
    }).promise;

    const imgData = tempCanvas.toDataURL('image/png', 1.0);
    
    // resize fabric canvas to match PDF
    this.canvas?.setWidth(viewport.width);
    this.canvas?.setHeight(viewport.height);
    this.canvas?.renderAll();
    
    this.canvas?.setBackgroundImage(imgData, () => {
        this.canvas?.renderAll();
    });

    console.log('viewport width:', viewport.width);
  }

  save(){ // save entire canvas as JSON
    const content = JSON.stringify(this.canvas?.toJSON());

    if(this.noteId){
      const updatedNote = {...this.note, content:content, name: this.noteName, pdfPath: this.pdfBase64} as Note;      
      this.noteService.updateNote(this.noteId, updatedNote).subscribe({
        next: () => console.log('Note Saved!'),
        error: (err) => console.error('Save failed', err)
      });
    }
    else {
      const newNote = {
        name: this.noteName,
        content: content,
        pdfPath: this.pdfBase64
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

  exportPdf() {
    const multiplier = 2; // export at 2x resolution
    const imgData = this.canvas?.toDataURL({
        multiplier: multiplier,
        format: 'png',
        quality: 1
    });
    if (!imgData) return;
    
    const pdf = new jsPDF({
        orientation: 'portrait',
        unit: 'px',
        format: [this.canvas!.width!, this.canvas!.height!]
    });
    
    pdf.addImage(imgData, 'PNG', 0, 0, this.canvas!.width!, this.canvas!.height!);
    const fileName = this.note?.name || this.noteName || 'note';
    pdf.save(`${fileName}.pdf`);
}

}
