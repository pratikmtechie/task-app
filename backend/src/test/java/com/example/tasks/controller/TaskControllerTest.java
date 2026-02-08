package com.example.tasks.controller;

import com.example.tasks.dto.TaskRequest;
import com.example.tasks.model.Task;
import com.example.tasks.model.TaskPriority;
import com.example.tasks.model.TaskStatus;
import com.example.tasks.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class TaskControllerTest {

    @Autowired
    private WebTestClient client;

    @Test
    void createAndFetchTask() {
        TaskRequest req = new TaskRequest("Demo task", "Test description", TaskStatus.TODO, TaskPriority.MEDIUM, "john");

        client.post().uri("/api/tasks")
                .bodyValue(req)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.title").isEqualTo("Demo task");
    }
}