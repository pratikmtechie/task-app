package com.example.tasks.service;
;

import com.example.tasks.dto.TaskRequest;
import com.example.tasks.dto.TaskResponse;
import com.example.tasks.exception.TaskNotFoundException;
import com.example.tasks.model.Task;
import com.example.tasks.model.TaskPriority;
import com.example.tasks.model.TaskStatus;
import com.example.tasks.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository repo;

    @InjectMocks
    private TaskServiceImpl service;

    @Test
    void create_ShouldReturnSavedTaskResponse() {
        TaskRequest req = new TaskRequest("Title", "Desc", TaskStatus.TODO, TaskPriority.LOW, "user1");
        Task savedTask = new Task(req.title(), req.description(), req.status(), req.priority(), req.assignee());
        savedTask.setId("123");

        when(repo.save(any(Task.class))).thenReturn(Mono.just(savedTask));

        Mono<TaskResponse> result = service.create(req);

        StepVerifier.create(result)
                .assertNext(res -> {
                    assertEquals("123", res.id());
                    assertEquals("Title", res.title());
                    assertEquals("Desc", res.description());
                    assertEquals(TaskStatus.TODO, res.status());
                    assertEquals(TaskPriority.LOW, res.priority());
                    assertEquals("user1", res.assignee());
                })
                .verifyComplete();

        ArgumentCaptor<Task> captor = ArgumentCaptor.forClass(Task.class);
        verify(repo).save(captor.capture());
        assertEquals("Title", captor.getValue().getTitle());
    }

    @Test
    void list_ShouldReturnAllTasksWithGivenStatus() {
        Task task1 = new Task("T1", "D1", TaskStatus.TODO, TaskPriority.LOW, "a");
        task1.setId("1");
        Task task2 = new Task("T2", "D2", TaskStatus.TODO, TaskPriority.MEDIUM, "b");
        task2.setId("2");

        when(repo.findAll(TaskStatus.TODO)).thenReturn(Flux.just(task1, task2));

        Flux<TaskResponse> result = service.list(TaskStatus.TODO);

        StepVerifier.create(result)
                .expectNextCount(2)
                .verifyComplete();

        verify(repo).findAll(TaskStatus.TODO);
    }

    @Test
    void getById_ShouldReturnTaskResponse_WhenTaskExists() {
        Task task = new Task("T", "D", TaskStatus.IN_PROGRESS, TaskPriority.HIGH, "userX");
        task.setId("abc");

        when(repo.findById("abc")).thenReturn(Mono.just(task));

        StepVerifier.create(service.getById("abc"))
                .assertNext(res -> {
                    assertEquals("abc", res.id());
                    assertEquals("T", res.title());
                })
                .verifyComplete();
    }

    @Test
    void getById_ShouldError_WhenTaskNotFound() {
        when(repo.findById("missing")).thenReturn(Mono.empty());

        StepVerifier.create(service.getById("missing"))
                .expectErrorSatisfies(err -> assertTrue(err instanceof TaskNotFoundException))
                .verify();
    }

    @Test
    void update_ShouldModifyTask_WhenTaskExists() {
        Task existing = new Task("Old", "OldDesc", TaskStatus.TODO, TaskPriority.LOW, "oldUser");
        existing.setId("upd123");

        TaskRequest req = new TaskRequest("New", "NewDesc", TaskStatus.DONE, TaskPriority.HIGH, "newUser");
        Task updatedTask = new Task(req.title(), req.description(), req.status(), req.priority(), req.assignee());
        updatedTask.setId("upd123");

        when(repo.findById("upd123")).thenReturn(Mono.just(existing));
        when(repo.save(any(Task.class))).thenReturn(Mono.just(updatedTask));

        StepVerifier.create(service.update("upd123", req))
                .assertNext(res -> {
                    assertEquals("upd123", res.id());
                    assertEquals("New", res.title());
                    assertEquals(TaskStatus.DONE, res.status());
                })
                .verifyComplete();

        verify(repo).save(existing);
    }

    @Test
    void update_ShouldError_WhenTaskNotFound() {
        TaskRequest req = new TaskRequest("New", "NewDesc", TaskStatus.DONE, TaskPriority.HIGH, "newUser");

        when(repo.findById("missing")).thenReturn(Mono.empty());

        StepVerifier.create(service.update("missing", req))
                .expectError(TaskNotFoundException.class)
                .verify();
    }

    @Test
    void delete_ShouldComplete_WhenTaskDeleted() {
        when(repo.deleteById("del123")).thenReturn(Mono.just(true));

        StepVerifier.create(service.delete("del123"))
                .verifyComplete();
    }

    @Test
    void delete_ShouldError_WhenTaskNotFound() {
        when(repo.deleteById("missing")).thenReturn(Mono.just(false));

        StepVerifier.create(service.delete("missing"))
                .expectError(TaskNotFoundException.class)
                .verify();
    }
}

