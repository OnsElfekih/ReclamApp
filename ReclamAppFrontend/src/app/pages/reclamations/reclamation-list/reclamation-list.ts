import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

import { ReclamationService } from '../../../core/services/reclamation.service';
import { AgentService } from '../../../core/services/agent.service';

import { Reclamation } from '../../../core/models/reclamation.model';
import { Agent } from '../../../core/models/agent.model';

@Component({
  selector: 'app-reclamation-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './reclamation-list.html'
})
export class ReclamationList implements OnInit {

  reclamations: any[] = [];
  agents: Agent[] = [];

  error = '';

  reclamationToDelete?: Reclamation;

  agentNom = '';
  clientNom = '';
  statut = '';
  date = '';

  constructor(
    private reclamationService: ReclamationService,
    private agentService: AgentService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadReclamations();
    this.loadAgents();
  }

  loadReclamations(): void {
    this.error = '';

    this.reclamationService.findAll().subscribe({
      next: (data) => {
        this.reclamations = data;
        this.cdr.detectChanges();
      },
      error: () => {
        this.error = 'Erreur de récupération des réclamations';
        this.cdr.detectChanges();
      }
    });
  }

  loadAgents(): void {
    this.agentService.findAll().subscribe({
      next: (data) => {
        this.agents = data;
        this.cdr.detectChanges();
      },
      error: () => {
        this.error = 'Erreur de récupération des agents';
        this.cdr.detectChanges();
      }
    });
  }

  addReclamation(): void {
    this.router.navigate(['/reclamations/nouvelle']);
  }

  editReclamation(id: number): void {
    this.router.navigate(['/reclamations/modifier', id]);
  }

  confirmDeleteReclamation(reclamation: Reclamation): void {
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
      }
    });
  }

  cancelDelete(): void {
    this.reclamationToDelete = undefined;
  }

  onSearchChange(): void {
    const emptySearch =
      this.agentNom.trim() === '' &&
      this.clientNom.trim() === '' &&
      this.statut === '' &&
      this.date === '';

    if (emptySearch) {
      this.loadReclamations();
      return;
    }

    this.reclamationService
      .search(this.agentNom, this.clientNom, this.statut, this.date)
      .subscribe({
        next: (data) => {
          this.error = '';
          this.reclamations = data;
          this.cdr.detectChanges();
        },
        error: () => {
          this.error = 'Erreur lors de la recherche';
          this.cdr.detectChanges();
        }
      });
  }

  affecterAgent(reclamationId: number, agentId: number): void {
    if (!agentId) {
      this.error = 'Veuillez choisir un agent';
      return;
    }

    this.reclamationService.affecterAgent(reclamationId, agentId).subscribe({
      next: () => {
        this.error = '';
        this.loadReclamations();
      },
      error: () => {
        this.error = 'Erreur lors de l’affectation';
        this.cdr.detectChanges();
      }
    });
  }
}