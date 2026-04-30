import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AgentService } from '../../../core/services/agent.service';
import { Agent } from '../../../core/models/agent.model';

@Component({
  selector: 'app-agent-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './agent-list.html'
})
export class AgentList implements OnInit {

  agents: Agent[] = [];

  constructor(
    private agentService: AgentService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.agentService.findAll().subscribe({
      next: (data) => {
        this.agents = data;
        this.cdr.detectChanges();
      },
      error: (err) => console.error(err)
    });
  }
}