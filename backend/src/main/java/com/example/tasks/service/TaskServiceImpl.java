package com.example.tasks.service;

import com.example.tasks.dto.TaskRequest;
import com.example.tasks.dto.TaskResponse;
import com.example.tasks.exception.TaskNotFoundException;
import com.example.tasks.model.Task;
import com.example.tasks.model.TaskStatus;
import com.example.tasks.repository.TaskRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Log4j2
@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository repo;

    public Mono<TaskResponse> create(TaskRequest req) {
        log.info("Create Task method execution started.");
        Task task = Task.builder().id(UUID.randomUUID().toString()).title(req.title())
                .description(req.description()).status(Objects.nonNull(req.status()) ? req.status() : TaskStatus.TODO)
                .priority(req.priority()).assignee(req.assignee()).build();
        return repo.save(task).map(this::toResponse);
    }

    public Flux<TaskResponse> list(TaskStatus status) {
        log.info("List tasks method execution started.");
        return repo.findAll(status).map(this::toResponse);
    }

    public Mono<TaskResponse> getById(String id) {
        log.info("Get task by id method execution started for id : {} ", id);
        return repo.findById(id)
                .switchIfEmpty(Mono.error(new TaskNotFoundException(id)))
                .map(this::toResponse);
    }

    public Mono<TaskResponse> update(String id, TaskRequest req) {
        log.info("Update task by id method execution started for id : {} ", id);
        return repo.findById(id)
                .switchIfEmpty(Mono.error(new TaskNotFoundException(id)))
                .flatMap(task -> {
                    task.setTitle(req.title());
                    task.setDescription(req.description());
                    task.setStatus(req.status());
                    task.setPriority(req.priority());
                    task.setAssignee(req.assignee());
                    task.setUpdatedAt(Instant.now());
                    return repo.save(task);
                })
                .map(this::toResponse);
    }

    public Mono<Void> delete(String id) {
        log.info("Delete task by id method execution started for id : {} ", id);
        return repo.deleteById(id)
                .flatMap(found -> found ? Mono.empty() : Mono.error(new TaskNotFoundException(id)));
    }

    private TaskResponse toResponse(Task t) {
        return TaskResponse.builder().id(t.getId()).title(t.getTitle()).description(t.getDescription()).status(t.getStatus())
                .priority(t.getPriority()).assignee(t.getAssignee()).createdAt(t.getCreatedAt()).updatedAt(t.getUpdatedAt()).build();

    }
}