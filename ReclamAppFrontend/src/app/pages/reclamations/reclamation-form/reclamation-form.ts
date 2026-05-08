import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { ReclamationService } from '../../../core/services/reclamation.service';

@Component({
  selector: 'app-reclamation-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './reclamation-form.html',
  styleUrl: './reclamation-form.css',
})
export class ReclamationForm implements OnInit {
  client: any;

  reclamation: any = {
    produit: '',
    description: '',
    note: null,
    statut: 'OUVERTE',
  };

  id!: number;
  isEditMode = false;
  error = '';

  constructor(
    private reclamationService: ReclamationService,
    private route: ActivatedRoute,
    private router: Router,
    private cdr: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    const connectedClient = localStorage.getItem('client');

    if (!connectedClient) {
      this.error = 'Aucun client connecté';
      return;
    }

    this.client = JSON.parse(connectedClient);

    this.route.paramMap.subscribe((params) => {
      const idParam = params.get('id');

      if (idParam) {
        this.id = Number(idParam);
        this.isEditMode = true;
        this.loadReclamation();
      }
    });
  }

  loadReclamation(): void {
    this.reclamationService.findById(this.id).subscribe({
      next: (data: any) => {
        if (data.clientId !== this.client.id) {
          this.error = 'Vous ne pouvez pas modifier cette réclamation';
          return;
        }

        this.reclamation = {
          produit: data.produit,
          description: data.description,
          note: data.note,
          statut: data.statut,
        };

        this.cdr.detectChanges();
      },

      error: () => {
        this.error = 'Réclamation introuvable';
        this.cdr.detectChanges();
      },
    });
  }

  saveReclamation(): void {
    const payload = {
      clientId: this.client.id,
      produit: this.reclamation.produit,
      description: this.reclamation.description,
      note: Number(this.reclamation.note),
      statut: this.reclamation.statut || 'OUVERTE',
    };

    if (this.isEditMode) {
      this.reclamationService.update(this.id, payload).subscribe({
        next: () => {
          this.goToMesReclamations();
        },

        error: () => {
          this.error = 'Erreur lors de la modification de la réclamation';
          this.cdr.detectChanges();
        },
      });
    } else {
      this.reclamationService.save(payload).subscribe({
        next: () => {
          this.goToMesReclamations();
        },

        error: () => {
          this.error = 'Erreur lors de l’ajout de la réclamation';
          this.cdr.detectChanges();
        },
      });
    }
  }

  goToMesReclamations(): void {
    this.router.navigateByUrl('/mes-reclamations');
  }

  cancel(): void {
    this.router.navigate(['/mes-reclamations']);
  }
}
