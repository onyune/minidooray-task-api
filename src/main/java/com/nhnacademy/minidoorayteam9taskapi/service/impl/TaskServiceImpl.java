package com.nhnacademy.minidoorayteam9taskapi.service.impl;

import com.nhnacademy.minidoorayteam9taskapi.dto.TagResponse;
import com.nhnacademy.minidoorayteam9taskapi.dto.task.TaskDetailResponse;
import com.nhnacademy.minidoorayteam9taskapi.dto.task.TaskListResponse;
import com.nhnacademy.minidoorayteam9taskapi.dto.task.TaskRequest;
import com.nhnacademy.minidoorayteam9taskapi.entity.Milestone;
import com.nhnacademy.minidoorayteam9taskapi.entity.Project;
import com.nhnacademy.minidoorayteam9taskapi.entity.Tag;
import com.nhnacademy.minidoorayteam9taskapi.entity.Task;
import com.nhnacademy.minidoorayteam9taskapi.entity.TaskTag;
import com.nhnacademy.minidoorayteam9taskapi.exception.UnauthorizedAccessException;
import com.nhnacademy.minidoorayteam9taskapi.repository.CommentRepository;
import com.nhnacademy.minidoorayteam9taskapi.repository.MilestoneRepository;
import com.nhnacademy.minidoorayteam9taskapi.repository.ProjectRepository;
import com.nhnacademy.minidoorayteam9taskapi.repository.ProjectUserRepository;
import com.nhnacademy.minidoorayteam9taskapi.repository.TagRepository;
import com.nhnacademy.minidoorayteam9taskapi.repository.TaskRepository;
import com.nhnacademy.minidoorayteam9taskapi.repository.TaskTagRepository;
import com.nhnacademy.minidoorayteam9taskapi.service.TaskService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final MilestoneRepository milestoneRepository;
    private final TagRepository tagRepository;
    private final TaskTagRepository taskTagRepository;
    private final ProjectUserRepository projectUserRepository;
    private final CommentRepository commentRepository;

    private void validateProjectMember(Long projectId, Long userId) {
        if (!projectUserRepository.existsByProjectIdAndUserId(projectId, userId)) {
            throw new UnauthorizedAccessException("User " + userId + " is not a member of project " + projectId);
        }
    }

    @Override
    public Page<TaskListResponse> getTasksByProjectId(Long projectId, Long userId, Pageable pageable) {
        validateProjectMember(projectId, userId);
        return taskRepository.findAllByProjectId(projectId, pageable)
            .map(task -> new TaskListResponse(task.getId(), task.getName()));
    }

    @Override
    public TaskDetailResponse getTask(Long projectId, Long taskId, Long userId) {
        validateProjectMember(projectId, userId);
        Task task = taskRepository.findById(taskId)
            .orElseThrow(() -> new RuntimeException("Task not found"));

        List<TagResponse> tagResponses = taskTagRepository.findAllByTaskId(taskId).stream()
            .map(taskTag -> new TagResponse(taskTag.getTag().getId(), taskTag.getTag().getName()))
                .toList();

        return new TaskDetailResponse(
            task.getId(),
            task.getName(),
            task.getContent(),
            task.getMilestone() != null ? task.getMilestone().getId() : null,
            task.getMilestone() != null ? task.getMilestone().getName() : null,
            tagResponses
        );
    }

    @Override
    @Transactional
    public Long createTask(Long projectId, TaskRequest request, Long userId) {
        validateProjectMember(projectId, userId);
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new RuntimeException("Project not found"));

        Milestone milestone = null;
        if (request.milestoneId() != null) {
            milestone = milestoneRepository.findById(request.milestoneId())
                .orElseThrow(() -> new RuntimeException("Milestone not found"));
        }

        Task task = Task.builder()
            .name(request.taskName())
            .content(request.taskContent())
            .project(project)
            .milestone(milestone)
            .build();

        taskRepository.save(task);

        if (request.tagIds() != null) {
            for (Long tagId : request.tagIds()) {
                Tag tag = tagRepository.findById(tagId)
                    .orElseThrow(() -> new RuntimeException("Tag not found"));
                TaskTag taskTag = TaskTag.builder()
                    .task(task)
                    .tag(tag)
                    .build();
                taskTagRepository.save(taskTag);
            }
        }

        return task.getId();
    }

    @Override
    @Transactional
    public void updateTask(Long projectId, Long taskId, TaskRequest request, Long userId) {
        validateProjectMember(projectId, userId);
        Task task = taskRepository.findById(taskId)
            .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setName(request.taskName());
        task.setContent(request.taskContent());

        if (request.milestoneId() != null) {
            Milestone milestone = milestoneRepository.findById(request.milestoneId())
                .orElseThrow(() -> new RuntimeException("Milestone not found"));
            task.setMilestone(milestone);
        } else {
            task.setMilestone(null);
        }

        taskTagRepository.deleteByTaskId(taskId);

        if (request.tagIds() != null) {
            for (Long tagId : request.tagIds()) {
                Tag tag = tagRepository.findById(tagId)
                    .orElseThrow(() -> new RuntimeException("Tag not found"));
                TaskTag taskTag = TaskTag.builder()
                    .task(task)
                    .tag(tag)
                    .build();
                taskTagRepository.save(taskTag);
            }
        }
    }

    @Override
    @Transactional
    public void deleteTask(Long projectId, Long taskId, Long userId) {
        validateProjectMember(projectId, userId);
        commentRepository.deleteByTaskId(taskId);
        taskTagRepository.deleteByTaskId(taskId);
        taskRepository.deleteById(taskId);
    }
}
