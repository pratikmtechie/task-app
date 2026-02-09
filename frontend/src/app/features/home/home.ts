import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  templateUrl: './home.html'
})
export class HomeComponent {
  constructor(private router: Router) {}

  goToList() {
    this.router.navigate(['/tasks']);
  }

  goToCreate() {
    this.router.navigate(['/tasks/new']);
  }
}
