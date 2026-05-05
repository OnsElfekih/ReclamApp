import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Suivi } from '../models/suivi.model';

@Injectable({ providedIn: 'root' })
export class SuiviService {
  private url = 'http://localhost:9090/api/suivis';
  constructor(private http: HttpClient) {}

  findAll(): Observable<Suivi[]> {
    return this.http.get<Suivi[]>(this.url);
  }
  findByReclamation(id: number): Observable<Suivi[]> {
    return this.http.get<Suivi[]>(`${this.url}/reclamation/${id}`);
  }
  save(suivi: any): Observable<Suivi> {
    return this.http.post<Suivi>(this.url, suivi);
  }
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}
