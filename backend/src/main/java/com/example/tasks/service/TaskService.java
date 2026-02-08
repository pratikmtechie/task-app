package com.example.tasks.service;

import com.example.tasks.dto.TaskRequest;
import com.example.tasks.dto.TaskResponse;
import com.example.tasks.model.TaskStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface TaskService {

    Mono<TaskResponse> create(TaskRequest req);

    Flux<TaskResponse> list(TaskStatus status);

    Mono<TaskResponse> getById(String id);

    Mono<TaskResponse> update(String id, TaskRequest req);

    Mono<Void> delete(String id);
}