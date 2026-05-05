import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

import { ClientService } from '../../../core/services/client.service';
import { Client } from '../../../core/models/client.model';

@Component({
  selector: 'app-client-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './client-list.html'
})
export class ClientList implements OnInit {

  clients: Client[] = [];
  keyword = '';
  error = '';

  constructor(
    private clientService: ClientService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadClients();
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
      }
    });
  }

  onSearchChange(): void {
  if (this.keyword.trim() === '') {
    this.loadClients();
    return;
  }

  this.clientService.search(this.keyword).subscribe({
    next: (data) => {
      this.clients = data;
      this.cdr.detectChanges();
    },
    error: () => {
      this.error = 'Erreur lors de la recherche';
      this.cdr.detectChanges();
    }
  });
}

  addClient(): void {
    this.router.navigate(['/clients/nouveau']);
  }

  editClient(id: number): void {
    this.router.navigate(['/clients/modifier', id]);
  }

  deleteClient(id: number): void {
    if (confirm('Voulez-vous supprimer ce client ?')) {
      this.clientService.delete(id).subscribe({
        next: () => {
          this.loadClients();
        },
        error: () => {
          this.error = 'Erreur lors de la suppression du client';
          this.cdr.detectChanges();
        }
      });
    }
  }
}