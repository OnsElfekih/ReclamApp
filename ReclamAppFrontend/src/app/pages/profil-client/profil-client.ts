import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { ClientService } from '../../core/services/client.service';
import { Client } from '../../core/models/client.model';

@Component({
  selector: 'app-profil-client',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './profil-client.html',
  styleUrls: ['./profil-client.css']
})
export class ProfilClient implements OnInit {

  client: Client = {
    nom: '',
    email: '',
    telephone: '',
    motDePasse: ''
  };

  message = '';
  error = '';

  constructor(
    private clientService: ClientService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    const connectedClient = localStorage.getItem('client');

    if (!connectedClient) {
      this.error = 'Aucun client connecté';
      return;
    }

    const clientData = JSON.parse(connectedClient);

    this.clientService.findById(clientData.id).subscribe({
      next: (data) => {
        this.client = {
          id: data.id,
          nom: data.nom,
          email: data.email,
          telephone: data.telephone,
          motDePasse: ''
        };

        this.cdr.detectChanges();
      },
      error: () => {
        this.error = 'Erreur lors de la récupération du profil';
        this.cdr.detectChanges();
      }
    });
  }

  updateProfil(): void {
    if (!this.client.id) {
      this.error = 'Client introuvable';
      return;
    }

    this.clientService.update(this.client.id, this.client).subscribe({
      next: (data) => {
        this.client = data;
        localStorage.setItem('client', JSON.stringify(data));

        this.message = 'Profil modifié avec succès';
        this.error = '';

        this.cdr.detectChanges();
      },
      error: () => {
        this.error = 'Erreur lors de la modification du profil';
        this.message = '';

        this.cdr.detectChanges();
      }
    });
  }
}