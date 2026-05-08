import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

import { ReclamationService } from '../../core/services/reclamation.service';

@Component({
  selector: 'app-mes-reclamations',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './mes-reclamations.html',
  styleUrls: ['./mes-reclamations.css']
})
export class MesReclamations implements OnInit {

  reclamations: any[] = [];
  client: any;
  error = '';

  constructor(
    private reclamationService: ReclamationService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    const connectedClient = localStorage.getItem('client');

    if (!connectedClient) {
      this.error = 'Aucun client connecté';
      return;
    }

    this.client = JSON.parse(connectedClient);

    this.loadReclamations();
  }

  loadReclamations(): void {
    this.reclamationService.getByClientId(this.client.id).subscribe({
      next: (data) => {
        this.reclamations = [...data];
        this.error = '';
        this.cdr.detectChanges();
      },
      error: () => {
        this.error = 'Erreur lors de la récupération des réclamations';
        this.cdr.detectChanges();
      }
    });
  }

  deleteReclamation(id: number): void {
    if (confirm('Voulez-vous supprimer cette réclamation ?')) {
      this.reclamationService.delete(id).subscribe({
        next: () => {
          this.loadReclamations();
        },
        error: () => {
          this.error = 'Erreur lors de la suppression';
          this.cdr.detectChanges();
        }
      });
    }
  }
}