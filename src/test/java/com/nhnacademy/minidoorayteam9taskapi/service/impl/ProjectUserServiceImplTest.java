package com.nhnacademy.minidoorayteam9taskapi.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import com.nhnacademy.minidoorayteam9taskapi.entity.Project;
import com.nhnacademy.minidoorayteam9taskapi.entity.ProjectUser;
import com.nhnacademy.minidoorayteam9taskapi.exception.ProjectNotFoundException;
import com.nhnacademy.minidoorayteam9taskapi.exception.ProjectUserAlreadyExistException;
import com.nhnacademy.minidoorayteam9taskapi.exception.ProjectUserNotFoundException;
import com.nhnacademy.minidoorayteam9taskapi.repository.ProjectRepository;
import com.nhnacademy.minidoorayteam9taskapi.repository.ProjectUserRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProjectUserServiceImplTest {

    @Mock
    private ProjectUserRepository projectUserRepository;
    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectUserServiceImpl projectUserService;

    private Project project;

    @BeforeEach
    void setUp() {
        project = Project.builder().name("P").build();
    }

    @Test
    void addProjectUser_Success() {
        when(projectRepository.existsById(anyLong())).thenReturn(true);
        when(projectUserRepository.existsByIdAndUserId(anyLong(), anyLong())).thenReturn(false);
        when(projectRepository.getProjectById(anyLong())).thenReturn(project);

        ProjectUser result = projectUserService.addProjectUser(1L, 1L, false);

        assertThat(result.getUserId()).isEqualTo(1L);
        verify(projectUserRepository).save(any(ProjectUser.class));
    }

    @Test
    void addProjectUser_ProjectNotFound() {
        when(projectRepository.existsById(anyLong())).thenReturn(false);

        assertThatThrownBy(() -> projectUserService.addProjectUser(1L, 1L, false))
            .isInstanceOf(ProjectNotFoundException.class);
    }

    @Test
    void addProjectUser_AlreadyExist() {
        when(projectRepository.existsById(anyLong())).thenReturn(true);
        when(projectUserRepository.existsByIdAndUserId(anyLong(), anyLong())).thenReturn(true);

        assertThatThrownBy(() -> projectUserService.addProjectUser(1L, 1L, false))
            .isInstanceOf(ProjectUserAlreadyExistException.class);
    }

    @Test
    void deleteProjectUser_Success() {
        ProjectUser projectUser = ProjectUser.builder().userId(1L).project(project).build();
        when(projectRepository.existsById(anyLong())).thenReturn(true);
        when(projectUserRepository.existsByIdAndUserId(anyLong(), anyLong())).thenReturn(true);
        when(projectUserRepository.getProjectUserByProjectIdAndUserId(anyLong(), anyLong())).thenReturn(projectUser);

        projectUserService.deleteProjectUser(1L, 1L);

        verify(projectUserRepository).delete(projectUser);
    }

    @Test
    void getProjectUsersByProjectId() {
        when(projectUserRepository.findAllByProjectId(anyLong())).thenReturn(List.of());
        projectUserService.getProjectUsersByProjectId(1L);
        verify(projectUserRepository).findAllByProjectId(1L);
    }

    @Test
    void getProjectUsersByUserId() {
        when(projectUserRepository.findAllByUserId(anyLong())).thenReturn(List.of());
        projectUserService.getProjectUsersByUserId(1L);
        verify(projectUserRepository).findAllByUserId(1L);
    }
}
