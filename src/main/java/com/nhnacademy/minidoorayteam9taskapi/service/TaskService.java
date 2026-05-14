package com.nhnacademy.minidoorayteam9taskapi.service;

import com.nhnacademy.minidoorayteam9taskapi.dto.task.TaskRequest;
import com.nhnacademy.minidoorayteam9taskapi.dto.task.TaskDetailResponse;
import com.nhnacademy.minidoorayteam9taskapi.dto.task.TaskListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {
    Page<TaskListResponse> getTasksByProjectId(Long projectId, Long userId, Pageable pageable);

    TaskDetailResponse getTask(Long projectId, Long taskId, Long userId);

    Long createTask(Long projectId, TaskRequest request, Long userId);

    void updateTask(Long projectId, Long taskId, TaskRequest request, Long userId);

    void deleteTask(Long projectId, Long taskId, Long userId);
}
