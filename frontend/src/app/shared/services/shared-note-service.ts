import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class SharedNoteService {
  
  private apiUrl = 'http://localhost:8080/api/shared';

  // Inject HttpClient to make HTTP calls
  constructor(private http: HttpClient) {}

  revokeAccess(noteId: number, userId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${noteId}/revoke/${userId}`);
  }

  updatePermission(noteId: number, userId: number, permission: string): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/${noteId}/permission/${userId}`, permission);
  }

}
