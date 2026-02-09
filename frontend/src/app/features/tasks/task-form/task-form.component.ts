import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { TaskService } from '../../../core/services/task.service';
import { catchError, of } from 'rxjs';
import { CommonModule } from '@angular/common';
import { TASK_PRIORITIES, TaskPriority } from '../../../core/models/task.model';


@Component({
  selector: 'app-task-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './task-form.component.html',
  styleUrls: ['./task-form.component.scss']
})
export class TaskFormComponent {
    
  priorities = TASK_PRIORITIES;
  loading = false;
  message: string | null = null;

  form!: FormGroup;

  constructor(private fb: FormBuilder, private taskService: TaskService) {
    this.form = this.fb.group({
      title: [''],
      priority: ['LOW']
    });
  }

  submit() {
    if (this.form.invalid) return;
    this.loading = true;
    this.message = null;

    this.taskService.create(this.form.value)
      .pipe(
        catchError(err => {
          this.message = err.message;
          return of(null);
        })
      )
      .subscribe(task => {
        if (task) {
          this.message = 'Task created successfully!';
          this.form.reset({ priority: 'MEDIUM' });
        }
        this.loading = false;
      });
  }
}