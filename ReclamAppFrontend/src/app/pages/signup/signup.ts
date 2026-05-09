import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';

import { ClientService } from '../../core/services/client.service';
import { Client } from '../../core/models/client.model';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './signup.html',
  styleUrls: ['./signup.css'],
})
export class Signup {
  client: Client = {
    nom: '',
    email: '',
    telephone: '',
    motDePasse: '',
  };

  error = '';
  message = '';

  constructor(
    private clientService: ClientService,
    private router: Router,
  ) {}

  signup(): void {
    this.clientService.save(this.client).subscribe({
      next: () => {
        this.message = 'Compte créé avec succès';
        this.error = '';

        setTimeout(() => {
          this.router.navigate(['/login']);
        }, 1000);
      },
      error: () => {
        this.error = 'Erreur lors de la création du compte';
        this.message = '';
      },
    });
  }
}
