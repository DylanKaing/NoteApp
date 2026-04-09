import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Note } from '../models/note';

@Injectable({
  providedIn: 'root',
})
export class NoteService {
  
  private apiUrl = 'http://localhost:8080/api/notes';

  // Inject HttpClient to make HTTP calls
  constructor(private http: HttpClient) {}

  createNote(note: Note, userId: number): Observable<Note> {
    return this.http.post<Note>(`${this.apiUrl}/user/${userId}`, note);
  }

  getNotesByUser(userId: number): Observable<Note[]> {
    return this.http.get<Note[]>(`${this.apiUrl}/user/${userId}`);
  }

  getNoteById(noteId: number): Observable<Note> {
    return this.http.get<Note>(`${this.apiUrl}/${noteId}`);
  }

  updateNote(noteId: number, note: Note): Observable<Note> {
    return this.http.put<Note>(`${this.apiUrl}/${noteId}`, note);
  }

  deleteNote(noteId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${noteId}`);
  }

  shareNote(noteId: number, userId:number, permission: string): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/${noteId}/share/${userId}`, permission);
  }

  getSharedNotes(userId: number): Observable<Note[]> {
    return this.http.get<Note[]>(`${this.apiUrl}/shared/${userId}`);
  }

}
