package com.nhnacademy.minidoorayteam9taskapi.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.nhnacademy.minidoorayteam9taskapi.entity.Project;
import com.nhnacademy.minidoorayteam9taskapi.entity.ProjectUser;
import com.nhnacademy.minidoorayteam9taskapi.repository.ProjectRepository;
import com.nhnacademy.minidoorayteam9taskapi.repository.ProjectUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {

    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private ProjectUserRepository projectUserRepository;

    @InjectMocks
    private ProjectServiceImpl projectService;

    @Test
    void createProject_Success() {
        when(projectRepository.save(any(Project.class))).thenAnswer(i -> i.getArgument(0));
        when(projectUserRepository.save(any(ProjectUser.class))).thenAnswer(i -> i.getArgument(0));

        Project result = projectService.createProject(1L, "New Project");

        assertThat(result.getName()).isEqualTo("New Project");
        verify(projectRepository).save(any(Project.class));
        verify(projectUserRepository).save(any(ProjectUser.class));
    }

    @Test
    void getProject_ById() {
        Project project = Project.builder().name("P").build();
        when(projectRepository.getProjectById(1L)).thenReturn(project);

        Project result = projectService.getProject(1L);

        assertThat(result.getName()).isEqualTo("P");
    }

    @Test
    void getProject_ByName() {
        Project project = Project.builder().name("P").build();
        when(projectRepository.getProjectByName("P")).thenReturn(project);

        Project result = projectService.getProject("P");

        assertThat(result.getName()).isEqualTo("P");
    }
}
