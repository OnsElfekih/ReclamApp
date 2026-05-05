import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { ReclamationService } from '../../../core/services/reclamation.service';
import { ClientService } from '../../../core/services/client.service';
import { Client } from '../../../core/models/client.model';

@Component({
  selector: 'app-reclamation-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './reclamation-form.html',
  styleUrl: './reclamation-form.css',
})
export class ReclamationForm implements OnInit {
  clients: Client[] = [];

  reclamation: any = {
    client: {
      id: null,
    },
    produit: '',
    description: '',
    note: null,
    statut: '',
  };

  id!: number;
  isEditMode = false;
  error = '';

  constructor(
    private reclamationService: ReclamationService,
    private clientService: ClientService,
    private route: ActivatedRoute,
    private router: Router,
    private cdr: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    this.loadClients();

    this.route.paramMap.subscribe((params) => {
      const idParam = params.get('id');

      if (idParam) {
        this.id = Number(idParam);
        this.isEditMode = true;
        this.loadReclamation();
      }
    });
  }

  loadClients(): void {
    this.clientService.findAll().subscribe({
      next: (data) => {
        this.clients = data;
        this.cdr.detectChanges();
      },
      error: () => {
        this.error = 'Erreur de récupération des clients';
        this.cdr.detectChanges();
      },
    });
  }

  loadReclamation(): void {
    this.reclamationService.findById(this.id).subscribe({
      next: (data) => {
        this.reclamation = {
          client: {
            id: data.client?.id,
          },
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
    if (!this.reclamation.client.id) {
      this.error = 'Veuillez choisir un client';
      return;
    }

    const payload = {
      client: {
        id: Number(this.reclamation.client.id),
      },
      produit: this.reclamation.produit,
      description: this.reclamation.description,
      note: Number(this.reclamation.note),
      statut: this.reclamation.statut,
    };

    if (this.isEditMode) {
      this.reclamationService.update(this.id, payload).subscribe({
        next: () => {
          this.router.navigate(['/reclamations']);
        },
        error: () => {
          this.error = 'Erreur lors de la modification de la réclamation';
          this.cdr.detectChanges();
        },
      });
    } else {
      this.reclamationService.save(payload).subscribe({
        next: () => {
          this.router.navigate(['/reclamations']);
        },
        error: () => {
          this.error = 'Erreur lors de l’ajout de la réclamation';
          this.cdr.detectChanges();
        },
      });
    }
  }

  cancel(): void {
    this.router.navigate(['/reclamations']);
  }
}
