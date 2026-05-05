import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

import { AgentService } from '../../../core/services/agent.service';
import { Agent } from '../../../core/models/agent.model';

@Component({
  selector: 'app-agent-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './agent-list.html',
})
export class AgentList implements OnInit {
  agentToDelete?: number;
  agents: Agent[] = [];
  keyword = '';
  error = '';

  constructor(
    private agentService: AgentService,
    private router: Router,
    private cdr: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    this.loadAgents();
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
      },
    });
  }

  onSearchChange(): void {
    if (this.keyword.trim() === '') {
      this.loadAgents();
      return;
    }

    this.agentService.search(this.keyword).subscribe({
      next: (data) => {
        this.agents = data;
        this.cdr.detectChanges();
      },
      error: () => {
        this.error = 'Erreur lors de la recherche';
        this.cdr.detectChanges();
      },
    });
  }

  addAgent(): void {
    this.router.navigate(['/agents/nouveau']);
  }

  editAgent(id: number): void {
    this.router.navigate(['/agents/modifier', id]);
  }
  confirmDeleteAgent(id: number): void {
    this.agentToDelete = id;
  }

  deleteAgent(): void {
    if (this.agentToDelete) {
      this.agentService.delete(this.agentToDelete).subscribe(() => {
        this.loadAgents();
        this.agentToDelete = undefined;
      });
    }
  }
}
