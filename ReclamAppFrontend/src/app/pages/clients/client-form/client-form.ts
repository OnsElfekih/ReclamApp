import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { ClientService } from '../../../core/services/client.service';
import { Client } from '../../../core/models/client.model';

@Component({
  selector: 'app-client-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './client-form.html',
})
export class ClientForm implements OnInit {
  client: Client = {
    nom: '',
    email: '',
    telephone: '',
  };

  id!: number;
  isEditMode = false;
  error = '';

  constructor(
    private clientService: ClientService,
    private route: ActivatedRoute,
    private router: Router,
    private cdr: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      const idParam = params.get('id');

      if (idParam) {
        this.id = Number(idParam);
        this.isEditMode = true;
        this.loadClient();
      }
    });
  }

  loadClient(): void {
    this.clientService.findById(this.id).subscribe({
      next: (data) => {
        this.client = {
          id: data.id,
          nom: data.nom,
          email: data.email,
          telephone: data.telephone,
        };

        this.cdr.detectChanges();
      },
      error: () => {
        this.error = 'Client introuvable';
        this.cdr.detectChanges();
      },
    });
  }

  saveClient(): void {
    if (this.isEditMode) {
      this.clientService.update(this.id, this.client).subscribe({
        next: () => {
          this.router.navigate(['/clients']);
        },
        error: () => {
          this.error = 'Erreur lors de la modification du client';
          this.cdr.detectChanges();
        },
      });
    } else {
      this.clientService.save(this.client).subscribe({
        next: () => {
          this.router.navigate(['/clients']);
        },
        error: () => {
          this.error = 'Erreur lors de l’ajout du client';
          this.cdr.detectChanges();
        },
      });
    }
  }

  cancel(): void {
    this.router.navigate(['/clients']);
  }
}
