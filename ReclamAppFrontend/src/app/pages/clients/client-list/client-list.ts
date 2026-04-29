import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClientService } from '../../../core/services/client.service';
import { Client } from '../../../core/models/client.model';

@Component({
  selector: 'app-client-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './client-list.html'
})
export class ClientList implements OnInit {

  clients: Client[] = [];
  error = '';

  constructor(
    private clientService: ClientService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    console.log('Début appel API');

    this.clientService.findAll().subscribe({
      next: (data) => {
        console.log('Données reçues :', data);
        this.clients = data;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Erreur API :', err);
        this.error = 'Erreur de récupération des clients';
        this.cdr.detectChanges();
      }
    });
  }
}