import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Agent } from '../models/agent.model';

@Injectable({ providedIn: 'root' })
export class AgentService {
  private url = 'http://localhost:9090/api/agents';
  constructor(private http: HttpClient) {}

  findAll(): Observable<Agent[]> {
    return this.http.get<Agent[]>(this.url);
  }
  findById(id: number): Observable<Agent> {
    return this.http.get<Agent>(`${this.url}/${id}`);
  }
  save(agent: Agent): Observable<Agent> {
    return this.http.post<Agent>(this.url, agent);
  }
  update(id: number, agent: Agent): Observable<Agent> {
    return this.http.put<Agent>(`${this.url}/${id}`, agent);
  }
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}