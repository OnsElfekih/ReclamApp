import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Reclamation, StatutReclamation } from '../models/reclamation.model';

@Injectable({ providedIn: 'root' })
export class ReclamationService {

  private url = 'http://localhost:9090/api/reclamations';

  constructor(private http: HttpClient) {}

  findAll(): Observable<Reclamation[]> {
    return this.http.get<Reclamation[]>(this.url);
  }

  findByStatut(statut: StatutReclamation): Observable<Reclamation[]> {
    return this.http.get<Reclamation[]>(`${this.url}?statut=${statut}`);
  }

  findById(id: number): Observable<Reclamation> {
    return this.http.get<Reclamation>(`${this.url}/${id}`);
  }

  save(r: any): Observable<Reclamation> {
    return this.http.post<Reclamation>(this.url, r);
  }

  update(id: number, r: any): Observable<Reclamation> {
    return this.http.put<Reclamation>(`${this.url}/${id}`, r);
  }

  affecter(reclamationId: number, agentId: number): Observable<Reclamation> {
    return this.http.put<Reclamation>(
      `${this.url}/${reclamationId}/affecter/${agentId}`,
      {}
    );
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }

  search(
    agentNom?: string,
    clientNom?: string,
    produit?: string,
    statut?: string,
    date?: string
  ): Observable<Reclamation[]> {
    let params = '';

    if (agentNom) params += `agentNom=${agentNom}&`;
    if (clientNom) params += `clientNom=${clientNom}&`;
    if (produit) params += `produit=${produit}&`;
    if (statut) params += `statut=${statut}&`;
    if (date) params += `date=${date}&`;

    return this.http.get<Reclamation[]>(`${this.url}/search?${params}`);
  }

  affecterAgent(reclamationId: number, agentId: number): Observable<Reclamation> {
    return this.http.put<Reclamation>(
      `${this.url}/${reclamationId}/affecter/${agentId}`,
      {}
    );
  }

  getByClientId(clientId: number): Observable<Reclamation[]> {
    return this.http.get<Reclamation[]>(
      `${this.url}/client/${clientId}`
    );
  }
}