import { Component, OnInit, OnDestroy, signal, NgModule } from '@angular/core';
import { Task, TaskStatus } from '../../../core/models/task.model';
import { TaskService } from '../../../core/services/task.service';
import { BehaviorSubject, Subscription, switchMap, startWith, of, catchError } from 'rxjs';
import { CommonModule } from '@angular/common';
import { LoadingSpinnerComponent } from '../../../shared/components/loading-spinner/loading-spinner.component';

@Component({
  selector: 'app-task-list',
  standalone: true,
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.scss'],
  imports: [CommonModule, LoadingSpinnerComponent]
})
export class TaskListComponent implements OnInit, OnDestroy {
  tasks = signal<Task[]>([]);
  loading = signal(false);
  error = signal<string | null>(null);
  statusFilter$ = new BehaviorSubject<TaskStatus | null>(null);

  private sub?: Subscription;

  constructor(private taskService: TaskService) {}

  ngOnInit() {
    this.sub = this.statusFilter$
      .pipe(
        startWith(null),
        switchMap(status => {
          this.loading.set(true);
          this.error.set(null);
          return this.taskService.list(status || undefined)
            .pipe(
              catchError(err => {
                this.error.set(err.message);
                return of([]);
              })
            );
        })
      )
      .subscribe(tasks => {
        this.tasks.set(tasks);
        this.loading.set(false);
      });
  }

  onFilterChange(status: string) {
    this.statusFilter$.next(status ? (status as TaskStatus) : null);
  }

  ngOnDestroy() {
    this.sub?.unsubscribe();
  }
}