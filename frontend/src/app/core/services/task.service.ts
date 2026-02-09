import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { catchError, map, throwError } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Task, CreateTaskRequest, TaskStatus } from '../models/task.model';

@Injectable({ providedIn: 'root' })
export class TaskService {
  // private http = inject(HttpClient);
  private baseUrl = `${environment.apiUrl}/tasks`;

  constructor(private http: HttpClient) {}

  list(status?: TaskStatus) {
    let params = new HttpParams();

  if (status !== undefined) {
    params = params.set('status', status);
  }

  return this.http.get<Task[]>(this.baseUrl, { params });
  }

  create(task: CreateTaskRequest) {
    return this.http.post<Task>(this.baseUrl, task).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse) {
    console.error('API Error:', error);
    return throwError(() => new Error(error.message || 'Unknown error'));
  }
}