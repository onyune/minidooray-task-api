package com.nhnacademy.minidoorayteam9taskapi.service.impl;

import com.nhnacademy.minidoorayteam9taskapi.entity.Project;
import com.nhnacademy.minidoorayteam9taskapi.entity.ProjectUser;
import com.nhnacademy.minidoorayteam9taskapi.repository.ProjectRepository;
import com.nhnacademy.minidoorayteam9taskapi.repository.ProjectUserRepository;
import com.nhnacademy.minidoorayteam9taskapi.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    private final ProjectUserRepository projectUserRepository;

    @Override
    @Transactional
    public Project createProject(long userId, String projectName) {
        Project project = new Project(projectName);
        projectRepository.save(project);
        ProjectUser projectUser = new ProjectUser(userId, true, project);
        projectUserRepository.save(projectUser);
        return project;
    }

    @Override
    @Transactional(readOnly = true)
    public Project getProject(long projectId) {
        return projectRepository.getProjectById(projectId);
    }

    @Override
    @Transactional(readOnly = true)
    public Project getProject(String projectName) {
        return projectRepository.getProjectByName(projectName);
    }

}
