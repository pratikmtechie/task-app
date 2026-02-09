import { Routes } from '@angular/router';
import { HomeComponent } from './features/home/home';
import { TaskListComponent } from './features/tasks/task-list/task-list.component';
import { TaskFormComponent } from './features/tasks/task-form/task-form.component';

export const routes: Routes = [
{ path: '', component: HomeComponent },
  { path: 'tasks', component: TaskListComponent },
  { path: 'tasks/new', component: TaskFormComponent },
  { path: '**', redirectTo: '' }
];