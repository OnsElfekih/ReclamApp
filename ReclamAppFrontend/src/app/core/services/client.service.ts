import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Client } from '../models/client.model';

@Injectable({ providedIn: 'root' })
export class ClientService {
  private url = 'https://reclamapp-backend-47755772899.us-central1.run.app/api/clients';
  constructor(private http: HttpClient) {}

  findAll(): Observable<Client[]> {
    return this.http.get<Client[]>(this.url);
  }
  findById(id: number): Observable<Client> {
    return this.http.get<Client>(`${this.url}/${id}`);
  }
  save(client: Client): Observable<Client> {
    return this.http.post<Client>(this.url, client);
  }
  update(id: number, client: Client): Observable<Client> {
    return this.http.put<Client>(`${this.url}/${id}`, client);
  }
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
  search(keyword: string): Observable<Client[]> {
    return this.http.get<Client[]>(`${this.url}/search?keyword=${keyword}`);
  }
  login(data: any): Observable<any> {
    return this.http.post<any>(`${this.url}/login`, data);
  }
}
