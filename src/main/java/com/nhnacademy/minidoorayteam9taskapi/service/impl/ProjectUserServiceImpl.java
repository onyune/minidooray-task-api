package com.nhnacademy.minidoorayteam9taskapi.service.impl;

import com.nhnacademy.minidoorayteam9taskapi.entity.ProjectUser;
import com.nhnacademy.minidoorayteam9taskapi.exception.ProjectNotFoundException;
import com.nhnacademy.minidoorayteam9taskapi.exception.ProjectUserAlreadyExistException;
import com.nhnacademy.minidoorayteam9taskapi.exception.ProjectUserNotFoundException;
import com.nhnacademy.minidoorayteam9taskapi.repository.ProjectRepository;
import com.nhnacademy.minidoorayteam9taskapi.repository.ProjectUserRepository;
import com.nhnacademy.minidoorayteam9taskapi.service.ProjectUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProjectUserServiceImpl implements ProjectUserService {
    private final ProjectUserRepository projectUserRepository;

    private final ProjectRepository projectRepository;

    @Override
    @Transactional
    public ProjectUser addProjectUser(long projectId, long userId, boolean isAdmin) {
        if (!projectRepository.existsById(projectId)) {
            throw new ProjectNotFoundException("해당 프로젝트를 찾을 수 없습니다. 프로젝트 ID : " + projectId);
        }
        if(projectUserRepository.existsByIdAndUserId(projectId, userId)){
            throw new ProjectUserAlreadyExistException("해당 프로젝트에 이미 해당 멤버가 있습니다.");
        }
        ProjectUser projectUser = new ProjectUser(userId, false, projectRepository.getProjectById(projectId));
        projectUserRepository.save(projectUser);
        return projectUser;
    }

    @Override
    @Transactional
    public void deleteProjectUser(long projectId, long userId) {
        if (!projectRepository.existsById(projectId)) {
            throw new ProjectNotFoundException("해당 프로젝트를 찾을 수 없습니다. 프로젝트 ID : " + projectId);
        }
        if (!projectUserRepository.existsByIdAndUserId(projectId, userId)) {
            throw new ProjectUserNotFoundException("해당 프로젝트 내 해당 유저를 찾을 수 없습니다. 프로젝트 ID : " + projectId + "유저 번호 : " + userId);
        }
        ProjectUser projectUser = projectUserRepository.getProjectUserByProjectIdAndUserId(projectId, userId);
        projectUserRepository.delete(projectUser);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectUser> getProjectUsersByProjectId(long projectId) {
        return projectUserRepository.findAllByProjectId(projectId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectUser> getProjectUsersByUserId(long userId) {
        return projectUserRepository.findAllByUserId(userId);
    }
}
