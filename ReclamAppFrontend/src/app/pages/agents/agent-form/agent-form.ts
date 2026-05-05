import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { AgentService } from '../../../core/services/agent.service';
import { Agent } from '../../../core/models/agent.model';

@Component({
  selector: 'app-agent-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './agent-form.html'
})
export class AgentForm implements OnInit {

  agent: Agent = {
    nom: '',
    competence: ''
  };

  id!: number;
  isEditMode = false;
  error = '';

  constructor(
    private agentService: AgentService,
    private route: ActivatedRoute,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const idParam = params.get('id');

      if (idParam) {
        this.id = Number(idParam);
        this.isEditMode = true;
        this.loadAgent();
      }
    });
  }

  loadAgent(): void {
    this.agentService.findById(this.id).subscribe({
      next: (data) => {
        this.agent = {
          id: data.id,
          nom: data.nom,
          competence: data.competence
        };

        this.cdr.detectChanges();
      },
      error: () => {
        this.error = 'Agent introuvable';
        this.cdr.detectChanges();
      }
    });
  }

  saveAgent(): void {
    if (this.isEditMode) {
      this.agentService.update(this.id, this.agent).subscribe({
        next: () => {
          this.router.navigate(['/agents']);
        },
        error: () => {
          this.error = 'Erreur lors de la modification de l’agent';
          this.cdr.detectChanges();
        }
      });
    } else {
      this.agentService.save(this.agent).subscribe({
        next: () => {
          this.router.navigate(['/agents']);
        },
        error: () => {
          this.error = 'Erreur lors de l’ajout de l’agent';
          this.cdr.detectChanges();
        }
      });
    }
  }

  cancel(): void {
    this.router.navigate(['/agents']);
  }
}