import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReclamationService } from '../../../core/services/reclamation.service';
import { Reclamation } from '../../../core/models/reclamation.model';

@Component({
  selector: 'app-reclamation-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './reclamation-list.html'
})
export class ReclamationList implements OnInit {

  reclamations: Reclamation[] = [];

  constructor(
    private reclamationService: ReclamationService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.reclamationService.findAll().subscribe({
      next: (data) => {
        console.log(data);
        this.reclamations = data;
        this.cdr.detectChanges();
      },
      error: (err) => console.error(err)
    });
  }
}