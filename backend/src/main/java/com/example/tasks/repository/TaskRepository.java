package com.example.tasks.repository;

import com.example.tasks.model.Task;
import com.example.tasks.model.TaskStatus;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Log4j2
@Repository
public class TaskRepository {
    private final Map<String, Task> store = new ConcurrentHashMap<>();

    public Mono<Task> save(Task task) {
        log.info("Saving task in DB. Task title is : {}", task.getTitle());
        store.put(task.getId(), task);
        return Mono.just(task);
    }

    public Flux<Task> findAll(TaskStatus status) {
        log.info("Fetching all tasks from DB for status : {}", status);
        return Flux.fromIterable(store.values())
                .filter(t -> Objects.isNull(status) || t.getStatus() == status);
    }

    public Mono<Task> findById(String id) {
        log.info("Fetching task from DB for id : {}", id);
        Task task = store.get(id);
        return Objects.nonNull(task) ? Mono.just(task) : Mono.empty();
    }

    public Mono<Boolean> deleteById(String id) {
        log.info("Deleting task from DB for id : {}", id);
        return Mono.justOrEmpty(store.remove(id)).map(t -> true).defaultIfEmpty(false);
    }
}