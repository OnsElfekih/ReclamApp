import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class RapportService {
  private url = 'https://reclamapp-backend-47755772899.us-central1.run.app/api/reclamations/rapport';

  constructor(private http: HttpClient) {}

  getRapport(): Observable<any> {
    return this.http.get<any>(this.url);
  }
}