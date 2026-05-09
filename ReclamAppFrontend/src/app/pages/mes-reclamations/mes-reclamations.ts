import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

import { ReclamationService } from '../../core/services/reclamation.service';

@Component({
  selector: 'app-mes-reclamations',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './mes-reclamations.html',
  styleUrls: ['./mes-reclamations.css'],
})
export class MesReclamations implements OnInit {
  reclamations: any[] = [];
  client: any;
  error = '';

  reclamationToDelete?: any;

  constructor(
    private reclamationService: ReclamationService,
    private cdr: ChangeDetectorRef,
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
      },
    });
  }

  confirmDeleteReclamation(reclamation: any): void {
    this.reclamationToDelete = reclamation;
  }

  deleteReclamation(): void {
    if (!this.reclamationToDelete?.id) return;

    this.reclamationService.delete(this.reclamationToDelete.id).subscribe({
      next: () => {
        this.loadReclamations();
        this.cancelDelete();
      },
      error: () => {
        this.error = 'Erreur lors de la suppression';
        this.cancelDelete();
        this.cdr.detectChanges();
      },
    });
  }

  cancelDelete(): void {
    this.reclamationToDelete = undefined;
  }
}
