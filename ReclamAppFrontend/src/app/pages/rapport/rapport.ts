import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';

import { RapportService } from '../../core/services/rapport.service';
import { SuiviService } from '../../core/services/suivi.service';
import { Suivi } from '../../core/models/suivi.model';

@Component({
  selector: 'app-rapport',
  standalone: true,
  imports: [CommonModule, DatePipe],
  templateUrl: './rapport.html',
  styleUrl: './rapport.css',
})
export class Rapport implements OnInit {
  rapport: any;
  statuts: any[] = [];
  suivis: Suivi[] = [];
  error = '';

  constructor(
    private rapportService: RapportService,
    private suiviService: SuiviService,
    private cdr: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    this.loadRapport();
    this.loadSuivis();
  }

  loadRapport(): void {
    this.rapportService.getRapport().subscribe({
      next: (data) => {
        this.rapport = data;
        this.statuts = Object.entries(data.parStatut).map(([statut, total]) => ({
          statut,
          total,
        }));
        this.cdr.detectChanges();
      },
      error: () => {
        this.error = 'Erreur lors du chargement du rapport satisfaction';
        this.cdr.detectChanges();
      },
    });
  }

  loadSuivis(): void {
    this.suiviService.findAll().subscribe({
      next: (data) => {
        this.suivis = data;
        this.cdr.detectChanges();
      },
      error: () => {
        this.error = 'Erreur lors du chargement des suivis';
        this.cdr.detectChanges();
      },
    });
  }
  printReport(): void {
    window.print();
  }
}
