import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterModule],
  template: `
    <nav class="navbar">
      <a routerLink="/">Home</a>
    </nav>
  `,
  styles: [`
    .navbar {
      display: flex;
      gap: 20px;       /* space between links */
      padding: 10px 20px;
      background-color: #f5f5f5;
      border-bottom: 1px solid #ccc;
    }

    .navbar a {
      text-decoration: none;
      color: #333;
      font-weight: bold;
    }

    .navbar a:hover {
      color: #007bff;
    }
  `]
})
export class NavbarComponent {}
