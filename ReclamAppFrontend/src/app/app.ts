import { Component } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';

import { Navbar } from './shared/navbar/navbar';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, Navbar, CommonModule],

  template: `
    <app-navbar *ngIf="!isLoginPage()"></app-navbar>

    <router-outlet></router-outlet>
  `,
})
export class App {
  constructor(public router: Router) {}

  isLoginPage(): boolean {
    return this.router.url === '/login' || this.router.url === '/signup';
  }
}
