package com.nhnacademy.minidoorayteam9taskapi.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhnacademy.minidoorayteam9taskapi.dto.milestone.MilestoneRequest;
import com.nhnacademy.minidoorayteam9taskapi.dto.milestone.MilestoneResponse;
import com.nhnacademy.minidoorayteam9taskapi.entity.Milestone;
import com.nhnacademy.minidoorayteam9taskapi.entity.Project;
import com.nhnacademy.minidoorayteam9taskapi.exception.MilestoneNotFoundException;
import com.nhnacademy.minidoorayteam9taskapi.repository.MilestoneRepository;
import com.nhnacademy.minidoorayteam9taskapi.repository.ProjectRepository;
import com.nhnacademy.minidoorayteam9taskapi.repository.ProjectUserRepository;
import com.nhnacademy.minidoorayteam9taskapi.repository.TaskRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MilestoneServiceImplTest {

    @Mock
    private MilestoneRepository milestoneRepository;
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private ProjectUserRepository projectUserRepository;
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private MilestoneServiceImpl milestoneService;

    private Project project;
    private Long projectId = 1L;
    private Long userId = 1L;

    @BeforeEach
    void setUp() {
        project = Project.builder().name("P").build();
    }

    @Test
    void getMilestonesByProjectId_Success() {
        Milestone milestone = Milestone.builder().name("M").project(project).build();
        when(projectUserRepository.existsByProjectIdAndUserId(projectId, userId)).thenReturn(true);
        when(milestoneRepository.findAllByProjectId(projectId)).thenReturn(List.of(milestone));

        List<MilestoneResponse> result = milestoneService.getMilestonesByProjectId(projectId, userId);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).name()).isEqualTo("M");
    }

    @Test
    void createMilestone_Success() {
        MilestoneRequest request = new MilestoneRequest("New M", null, null);
        when(projectUserRepository.existsByProjectIdAndUserId(projectId, userId)).thenReturn(true);
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(milestoneRepository.save(any(Milestone.class))).thenAnswer(i -> {
            Milestone m = i.getArgument(0);
            return m;
        });

        milestoneService.createMilestone(projectId, request, userId);

        verify(milestoneRepository).save(any(Milestone.class));
    }

    @Test
    void updateMilestone_Success() {
        Milestone milestone = Milestone.builder().name("Old").project(project).build();
        MilestoneRequest request = new MilestoneRequest("Updated", null, null);

        when(projectUserRepository.existsByProjectIdAndUserId(projectId, userId)).thenReturn(true);
        when(milestoneRepository.findById(anyLong())).thenReturn(Optional.of(milestone));

        milestoneService.updateMilestone(projectId, 1L, request, userId);

        assertThat(milestone.getName()).isEqualTo("Updated");
    }

    @Test
    void deleteMilestone_Success() {
        when(projectUserRepository.existsByProjectIdAndUserId(projectId, userId)).thenReturn(true);
        when(milestoneRepository.existsById(1L)).thenReturn(true);
        when(taskRepository.findAllByMilestoneId(1L)).thenReturn(Collections.emptyList());

        milestoneService.deleteMilestone(projectId, 1L, userId);

        verify(milestoneRepository).deleteById(1L);
    }

    @Test
    void deleteMilestone_NotFound() {
        when(projectUserRepository.existsByProjectIdAndUserId(projectId, userId)).thenReturn(true);
        when(milestoneRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> milestoneService.deleteMilestone(projectId, 1L, userId))
            .isInstanceOf(MilestoneNotFoundException.class);
    }
}
