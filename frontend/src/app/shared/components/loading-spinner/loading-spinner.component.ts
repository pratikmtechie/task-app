import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-loading-spinner',
  standalone: true,   // ✅ key part
  imports: [CommonModule], // if it uses common directives like ngIf
  template: `<div class="spinner">Loading...</div>`,
  styles: [`
    .spinner {
      text-align: center;
      padding: 1rem;
      font-weight: bold;
    }
  `]
})
export class LoadingSpinnerComponent {}