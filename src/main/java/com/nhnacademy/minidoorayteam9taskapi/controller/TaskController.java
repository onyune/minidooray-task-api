package com.nhnacademy.minidoorayteam9taskapi.controller;

import com.nhnacademy.minidoorayteam9taskapi.dto.task.TaskRequest;
import com.nhnacademy.minidoorayteam9taskapi.dto.task.TaskDetailResponse;
import com.nhnacademy.minidoorayteam9taskapi.dto.task.TaskListResponse;
import com.nhnacademy.minidoorayteam9taskapi.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects/{projectId}/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public Page<TaskListResponse> getTasks(@PathVariable Long projectId,
                                            @RequestHeader("X-USER-ID") Long userId,
                                            Pageable pageable) {
        return taskService.getTasksByProjectId(projectId, userId, pageable);
    }

    @GetMapping("/{taskId}")
    public TaskDetailResponse getTask(@PathVariable Long projectId,
                                       @PathVariable Long taskId,
                                       @RequestHeader("X-USER-ID") Long userId) {
        return taskService.getTask(projectId, taskId, userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long createTask(@PathVariable Long projectId,
                            @RequestBody TaskRequest request,
                            @RequestHeader("X-USER-ID") Long userId) {
        return taskService.createTask(projectId, request, userId);
    }

    @PutMapping("/{taskId}")
    public void updateTask(@PathVariable Long projectId,
                            @PathVariable Long taskId,
                            @RequestBody TaskRequest request,
                            @RequestHeader("X-USER-ID") Long userId) {
        taskService.updateTask(projectId, taskId, request, userId);
    }

    @DeleteMapping("/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long projectId,
                            @PathVariable Long taskId,
                            @RequestHeader("X-USER-ID") Long userId) {
        taskService.deleteTask(projectId, taskId, userId);
    }
}
