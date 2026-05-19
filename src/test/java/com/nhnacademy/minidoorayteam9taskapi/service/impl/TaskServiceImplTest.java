package com.nhnacademy.minidoorayteam9taskapi.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import com.nhnacademy.minidoorayteam9taskapi.dto.task.TaskDetailResponse;
import com.nhnacademy.minidoorayteam9taskapi.dto.task.TaskListResponse;
import com.nhnacademy.minidoorayteam9taskapi.dto.task.TaskRequest;
import com.nhnacademy.minidoorayteam9taskapi.entity.*;
import com.nhnacademy.minidoorayteam9taskapi.exception.UnauthorizedAccessException;
import com.nhnacademy.minidoorayteam9taskapi.repository.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private MilestoneRepository milestoneRepository;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private TaskTagRepository taskTagRepository;
    @Mock
    private ProjectUserRepository projectUserRepository;
    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Project project;
    private Long projectId = 1L;
    private Long userId = 1L;

    @BeforeEach
    void setUp() {
        project = Project.builder().name("Project").build();
    }

    @Test
    void getTasksByProjectId_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        Task task = Task.builder().name("Task").content("Content").project(project).build();
        Page<Task> taskPage = new PageImpl<>(List.of(task));

        when(projectUserRepository.existsByProjectIdAndUserId(projectId, userId)).thenReturn(true);
        when(taskRepository.findAllByProjectId(projectId, pageable)).thenReturn(taskPage);

        Page<TaskListResponse> result = taskService.getTasksByProjectId(projectId, userId, pageable);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).taskName()).isEqualTo("Task");
    }

    @Test
    void getTasksByProjectId_Unauthorized() {
        when(projectUserRepository.existsByProjectIdAndUserId(projectId, userId)).thenReturn(false);

        assertThatThrownBy(() -> taskService.getTasksByProjectId(projectId, userId, PageRequest.of(0, 10)))
            .isInstanceOf(UnauthorizedAccessException.class);
    }

    @Test
    void getTask_Success() {
        Task task = Task.builder().name("Task").content("Content").project(project).build();
        when(projectUserRepository.existsByProjectIdAndUserId(projectId, userId)).thenReturn(true);
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));
        when(taskTagRepository.findAllByTaskId(anyLong())).thenReturn(Collections.emptyList());

        TaskDetailResponse result = taskService.getTask(projectId, 1L, userId);

        assertThat(result.taskName()).isEqualTo("Task");
        assertThat(result.taskContent()).isEqualTo("Content");
    }

    @Test
    void getTask_NotFound() {
        when(projectUserRepository.existsByProjectIdAndUserId(projectId, userId)).thenReturn(true);
        when(taskRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.getTask(projectId, 1L, userId))
            .isInstanceOf(RuntimeException.class)
            .hasMessage("Task not found");
    }

    @Test
    void createTask_Success() {
        TaskRequest request = new TaskRequest("New Task", "New Content", null, List.of(1L));
        Tag tag = Tag.builder().name("Tag").project(project).build();

        when(projectUserRepository.existsByProjectIdAndUserId(projectId, userId)).thenReturn(true);
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(tagRepository.findById(1L)).thenReturn(Optional.of(tag));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        taskService.createTask(projectId, request, userId);

        verify(taskRepository, times(1)).save(any(Task.class));
        verify(taskTagRepository, times(1)).save(any(TaskTag.class));
    }

    @Test
    void createTask_ProjectNotFound() {
        TaskRequest request = new TaskRequest("New Task", "New Content", null, null);
        when(projectUserRepository.existsByProjectIdAndUserId(projectId, userId)).thenReturn(true);
        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.createTask(projectId, request, userId))
            .isInstanceOf(RuntimeException.class)
            .hasMessage("Project not found");
    }

    @Test
    void createTask_MilestoneNotFound() {
        TaskRequest request = new TaskRequest("New Task", "New Content", 1L, null);
        when(projectUserRepository.existsByProjectIdAndUserId(projectId, userId)).thenReturn(true);
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(milestoneRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.createTask(projectId, request, userId))
            .isInstanceOf(RuntimeException.class)
            .hasMessage("Milestone not found");
    }

    @Test
    void updateTask_Success() {
        Task task = Task.builder().name("Old").content("Old").project(project).build();
        TaskRequest request = new TaskRequest("Updated", "Updated", null, null);

        when(projectUserRepository.existsByProjectIdAndUserId(projectId, userId)).thenReturn(true);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        taskService.updateTask(projectId, 1L, request, userId);

        assertThat(task.getName()).isEqualTo("Updated");
        verify(taskTagRepository).deleteByTaskId(1L);
    }

    @Test
    void updateTask_WithMilestone() {
        Task task = Task.builder().name("Old").content("Old").project(project).build();
        Milestone milestone = Milestone.builder().name("M").project(project).build();
        TaskRequest request = new TaskRequest("Updated", "Updated", 1L, null);

        when(projectUserRepository.existsByProjectIdAndUserId(projectId, userId)).thenReturn(true);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(milestoneRepository.findById(1L)).thenReturn(Optional.of(milestone));

        taskService.updateTask(projectId, 1L, request, userId);

        assertThat(task.getMilestone()).isEqualTo(milestone);
    }

    @Test
    void deleteTask_Success() {
        when(projectUserRepository.existsByProjectIdAndUserId(projectId, userId)).thenReturn(true);

        taskService.deleteTask(projectId, 1L, userId);

        verify(commentRepository).deleteByTaskId(1L);
        verify(taskTagRepository).deleteByTaskId(1L);
        verify(taskRepository).deleteById(1L);
    }
}
