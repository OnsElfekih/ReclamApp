import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ClientService } from '../../core/services/client.service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './login.html',
  styleUrls: ['./login.css'],
})
export class Login {
  email = '';
  motDePasse = '';
  error = '';

  constructor(
    private clientService: ClientService,
    private router: Router,
  ) {}

  login() {
    const data = {
      email: this.email,
      motDePasse: this.motDePasse,
    };

    this.clientService.login(data).subscribe({
      next: (res: any) => {
        localStorage.setItem('role', res.role);
        if (res.role === 'CLIENT') {
          localStorage.setItem('client', JSON.stringify(res.client));
          localStorage.setItem('role', res.role);

          this.router.navigate(['/profil-client']);
        }

        if (res.role === 'ADMIN') {
          this.router.navigate(['/reclamations']);
        }
      },

      error: () => {
        this.error = 'Email ou mot de passe incorrect';
      },
    });
  }
}
