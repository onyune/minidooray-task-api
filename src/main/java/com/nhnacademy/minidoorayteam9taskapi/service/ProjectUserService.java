package com.nhnacademy.minidoorayteam9taskapi.service;

import com.nhnacademy.minidoorayteam9taskapi.entity.ProjectUser;

import java.util.List;

public interface ProjectUserService {

    // 프로젝트 멤버 가입
    ProjectUser addProjectUser(long projectId, long userId, boolean isAdmin);

    // 프로젝트 멤버 삭제
    void deleteProjectUser(long projectId, long userId);

    // 프로젝트에 속한 멤버 리스트 조회
    List<ProjectUser> getProjectUsersByProjectId(long projectId);

    List<ProjectUser> getProjectUsersByUserId(long userId);
}
