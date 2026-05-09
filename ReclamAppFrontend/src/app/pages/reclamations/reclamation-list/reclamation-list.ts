import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

import { ReclamationService } from '../../../core/services/reclamation.service';
import { AgentService } from '../../../core/services/agent.service';
import { SuiviService } from '../../../core/services/suivi.service';

import { Reclamation } from '../../../core/models/reclamation.model';
import { Agent } from '../../../core/models/agent.model';

@Component({
  selector: 'app-reclamation-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './reclamation-list.html',
})
export class ReclamationList implements OnInit {
  reclamations: any[] = [];
  agents: Agent[] = [];

  error = '';
  success = '';

  reclamationToDelete?: Reclamation;

  agentNom = '';
  clientNom = '';
  statut = '';
  date = '';
  produit = '';

  constructor(
    private reclamationService: ReclamationService,
    private agentService: AgentService,
    private suiviService: SuiviService,
    private router: Router,
    private cdr: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    this.loadReclamations();
    this.loadAgents();
  }

  loadReclamations(): void {
    this.error = '';

    this.reclamationService.findAll().subscribe({
      next: (data) => {
        this.reclamations = data.map((r: any) => ({
          ...r,
          suiviAction: null,
          suiviMessage: '',
          selectedAgentId: r.agentId ?? null,
        }));

        this.cdr.detectChanges();
      },
      error: () => {
        this.error = 'Erreur de récupération des réclamations';
        this.cdr.detectChanges();
      },
    });
  }
  loadAgents(): void {
    this.agentService.findAll().subscribe({
      next: (data) => {
        console.log('Agents récupérés :', data);
        this.agents = data;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.log('Erreur agents :', err);
        this.error = 'Erreur de récupération des agents';
        this.cdr.detectChanges();
      },
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
      },
    });
  }

  cancelDelete(): void {
    this.reclamationToDelete = undefined;
  }

  onSearchChange(): void {
    const emptySearch =
      this.agentNom.trim() === '' &&
      this.clientNom.trim() === '' &&
      this.produit.trim() === '' &&
      this.statut === '' &&
      this.date === '';

    if (emptySearch) {
      this.loadReclamations();
      return;
    }

    this.reclamationService
      .search(this.agentNom, this.clientNom, this.produit, this.statut, this.date)
      .subscribe({
        next: (data) => {
          this.error = '';
          this.reclamations = data;
          this.cdr.detectChanges();
        },
        error: () => {
          this.error = 'Erreur lors de la recherche';
          this.cdr.detectChanges();
        },
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
        this.success = 'Agent affecté avec succès';
        this.loadReclamations();
      },
      error: () => {
        this.error = 'Erreur lors de l’affectation';
        this.cdr.detectChanges();
      },
    });
  }

  ajouterSuivi(r: any): void {
    if (!r.id) {
      this.error = 'Réclamation introuvable';
      return;
    }

    if (!r.selectedAgentId && !r.agentId) {
      this.error = 'Veuillez choisir un agent';
      return;
    }

    if (!r.suiviAction || !r.suiviMessage) {
      this.error = 'Veuillez saisir une action et un message';
      return;
    }

    const payload = {
      reclamationId: r.id,
      agentId: r.selectedAgentId || r.agentId,
      action: r.suiviAction,
      message: r.suiviMessage,
    };

    this.suiviService.save(payload).subscribe({
      next: () => {
        this.error = '';
        this.success = 'Suivi ajouté avec succès';
        r.suiviAction = null;
        r.suiviMessage = '';
        this.cdr.detectChanges();
      },
      error: () => {
        this.error = 'Erreur lors de l’ajout du suivi';
        this.cdr.detectChanges();
      },
    });
  }
}
