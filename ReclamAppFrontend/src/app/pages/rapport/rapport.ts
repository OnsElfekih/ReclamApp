import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule,DatePipe } from '@angular/common';
import { SuiviService } from '../../core/services/suivi.service';
import { Suivi } from '../../core/models/suivi.model';

@Component({
  selector: 'app-rapport',
  standalone: true,
  imports: [CommonModule, DatePipe],
  templateUrl: './rapport.html'
})
export class Rapport implements OnInit {

  suivis: Suivi[] = [];

  constructor(
    private suiviService: SuiviService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.suiviService.findAll().subscribe({
      next: (data) => {
        console.log(data);
        this.suivis = data;
        this.cdr.detectChanges();
      },
      error: (err) => console.error(err)
    });
  }
}