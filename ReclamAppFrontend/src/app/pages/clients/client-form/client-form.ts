import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { ClientService } from '../../../core/services/client.service';
import { Client } from '../../../core/models/client.model';

@Component({
  selector: 'app-client-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './client-form.html'
})
export class ClientForm implements OnInit {

  client: Client = {
    nom: '',
    email: '',
    telephone: ''
  };

  id?: number;
  isEditMode = false;
  error = '';

  constructor(
    private clientService: ClientService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');

    if (idParam) {
      this.id = Number(idParam);
      this.isEditMode = true;
      this.loadClient(this.id);
    }
  }

  loadClient(id: number): void {
    this.clientService.findById(id).subscribe({
      next: (data) => {
        this.client = data;
      },
      error: () => {
        this.error = 'Client introuvable';
      }
    });
  }

  saveClient(): void {
    if (this.isEditMode && this.id) {
      this.clientService.update(this.id, this.client).subscribe({
        next: () => {
          this.router.navigate(['/clients']);
        },
        error: () => {
          this.error = 'Erreur lors de la modification du client';
        }
      });
    } else {
      this.clientService.save(this.client).subscribe({
        next: () => {
          this.router.navigate(['/clients']);
        },
        error: () => {
          this.error = 'Erreur lors de l’ajout du client';
        }
      });
    }
  }

  cancel(): void {
    this.router.navigate(['/clients']);
  }
}