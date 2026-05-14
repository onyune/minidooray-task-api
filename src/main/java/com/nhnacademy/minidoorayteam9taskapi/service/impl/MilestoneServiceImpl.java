package com.nhnacademy.minidoorayteam9taskapi.service.impl;

import com.nhnacademy.minidoorayteam9taskapi.dto.milestone.MilestoneRequest;
import com.nhnacademy.minidoorayteam9taskapi.dto.milestone.MilestoneResponse;
import com.nhnacademy.minidoorayteam9taskapi.entity.Milestone;
import com.nhnacademy.minidoorayteam9taskapi.entity.Project;
import com.nhnacademy.minidoorayteam9taskapi.entity.Task;
import com.nhnacademy.minidoorayteam9taskapi.exception.MilestoneNotFoundException;
import com.nhnacademy.minidoorayteam9taskapi.exception.UnauthorizedAccessException;
import com.nhnacademy.minidoorayteam9taskapi.repository.MilestoneRepository;
import com.nhnacademy.minidoorayteam9taskapi.repository.ProjectRepository;
import com.nhnacademy.minidoorayteam9taskapi.repository.ProjectUserRepository;
import com.nhnacademy.minidoorayteam9taskapi.repository.TaskRepository;
import com.nhnacademy.minidoorayteam9taskapi.service.MilestoneService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MilestoneServiceImpl implements MilestoneService {
    private final MilestoneRepository milestoneRepository;
    private final ProjectRepository projectRepository;
    private final ProjectUserRepository projectUserRepository;
    private final TaskRepository taskRepository;

    private void validateProjectMember(Long projectId, Long userId) {
        if (!projectUserRepository.existsByProjectIdAndUserId(projectId, userId)) {
            throw new UnauthorizedAccessException("User: " + userId + " 은 프로젝트 멤버가 아닙니다. " + projectId);
        }
    }

    @Override
    public List<MilestoneResponse> getMilestonesByProjectId(Long projectId, Long userId) {
        validateProjectMember(projectId, userId);
        return milestoneRepository.findAllByProjectId(projectId).stream()
                .map(milestone -> new MilestoneResponse(
                        milestone.getId(),
                        milestone.getName(),
                        milestone.getStartAt(),
                        milestone.getEndAt()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Long createMilestone(Long projectId, MilestoneRequest request, Long userId) {
        validateProjectMember(projectId, userId);
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        Milestone milestone = Milestone.builder()
                .name(request.name())
                .startAt(request.startAt())
                .endAt(request.endAt())
                .project(project)
                .build();

        return milestoneRepository.save(milestone).getId();
    }

    @Override
    @Transactional
    public void updateMilestone(Long projectId, Long milestoneId, MilestoneRequest request, Long userId) {
        validateProjectMember(projectId, userId);
        Milestone milestone = milestoneRepository.findById(milestoneId)
                .orElseThrow(() -> new MilestoneNotFoundException(milestoneId));

        milestone.setName(request.name());
        milestone.setStartAt(request.startAt());
        milestone.setEndAt(request.endAt());
        milestoneRepository.save(milestone);
    }

    @Override
    @Transactional
    public void deleteMilestone(Long projectId, Long milestoneId, Long userId) {
        validateProjectMember(projectId, userId);
        if (!milestoneRepository.existsById(milestoneId)) {
            throw new MilestoneNotFoundException(milestoneId);
        }
        List<Task> linkedTasks = taskRepository.findAllByMilestoneId(milestoneId);
        for (Task task : linkedTasks) {
            task.setMilestone(null);
        }
        milestoneRepository.deleteById(milestoneId);
    }
}
