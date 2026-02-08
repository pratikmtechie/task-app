package com.example.tasks.controller;

import com.example.tasks.dto.TaskRequest;
import com.example.tasks.dto.TaskResponse;
import com.example.tasks.model.TaskStatus;
import com.example.tasks.service.TaskService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private TaskService service;

    @PostMapping
    public Mono<TaskResponse> create(@Valid @RequestBody TaskRequest request) {
        log.info("Create Task API called.");
        return service.create(request);
    }

    @GetMapping
    public Flux<TaskResponse> list(@RequestParam(value = "status", required = false) TaskStatus status) {
        log.info("List tasks API called with status as : {} ", status);
        return service.list(status);
    }

    @GetMapping("/{id}")
    public Mono<TaskResponse> get(@PathVariable("id") String id) {
        log.info("Get Task API called for id : {} ", id);
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public Mono<TaskResponse> update(@PathVariable("id") String id, @Valid @RequestBody TaskRequest req) {
        log.info("Update Task API called for id : {} ", id);
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable("id") String id) {
        log.info("Delete Task API called for id : {} ", id);
        return service.delete(id);
    }
}