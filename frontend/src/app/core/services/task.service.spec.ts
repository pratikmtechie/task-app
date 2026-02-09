// import { TestBed } from '@angular/core/testing';
// import {
//   HttpClientTestingModule,
//   HttpTestingController
// } from '@angular/common/http/testing';

// import { TaskService } from './task.service';
// import { environment } from '../../../environments/environment';
// import { Task } from '../models/task.model';

// describe('TaskService', () => {
//   let service: TaskService;
//   let httpMock: HttpTestingController;

//   const baseUrl = `${environment.apiUrl}/tasks`;

//   beforeEach(() => {
//     TestBed.configureTestingModule({
//       imports: [HttpClientTestingModule]
//     });

//     service = TestBed.inject(TaskService);
//     httpMock = TestBed.inject(HttpTestingController);
//   });

//   afterEach(() => {
//     httpMock.verify();
//   });

//   it('should fetch all tasks without status filter', () => {
//     const mockTasks: Task[] = [
//       {
//           id: '1', title: 'Task 1', status: 'TODO',
//           priority: 'LOW',
//           createdAt: '',
//           updatedAt: ''
//       },
//       {
//           id: '2', title: 'Task 2', status: 'DONE',
//           priority: 'LOW',
//           createdAt: '',
//           updatedAt: ''
//       }
//     ];

//     service.list().subscribe(tasks => {
//       expect(tasks).toEqual(mockTasks);
//     });

//     const req = httpMock.expectOne(baseUrl);
//     expect(req.request.method).toBe('GET');
//     expect(req.request.params.keys().length).toBe(0);

//     req.flush(mockTasks);
//   });

//   it('should fetch tasks with status filter', () => {
//     service.list('TODO').subscribe();

//     const req = httpMock.expectOne(
//       r => r.url === baseUrl && r.params.get('status') === 'TODO'
//     );

//     expect(req.request.method).toBe('GET');
//     req.flush([]);
//   });

//   it('should create a new task', () => {
//     const request: Task = {
//         id: '123',
//         title: 'New Task',
//         status: 'TODO',
//         priority: 'LOW',
//         createdAt: '',
//         updatedAt: ''
//     };

//     const response: Task = {
//         id: '123',
//         title: 'New Task',
//         status: 'TODO',
//         priority: 'LOW',
//         createdAt: '',
//         updatedAt: ''
//     };

//     service.create(request).subscribe(task => {
//       expect(task).toEqual(response);
//     });

//     const req = httpMock.expectOne(baseUrl);
//     expect(req.request.method).toBe('POST');
//     expect(req.request.body).toEqual(request);

//     req.flush(response);
//   });

//   it('should handle API errors when creating a task', () => {
//     spyOn(console, 'error');

//     service.create({
//         title: 'Fail',
//         priority: 'LOW'
//     }).subscribe({
//       next: () => fail('Expected error'),
//       error: err => {
//         expect(err).toBeTruthy();
//         expect(err.message).toContain('Server error');
//       }
//     });

//     const req = httpMock.expectOne(baseUrl);
//     req.flush(
//       { message: 'Server error' },
//       { status: 500, statusText: 'Internal Server Error' }
//     );
//   });
// });
