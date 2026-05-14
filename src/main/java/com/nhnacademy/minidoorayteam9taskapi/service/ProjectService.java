package com.nhnacademy.minidoorayteam9taskapi.service;

import com.nhnacademy.minidoorayteam9taskapi.entity.Project;

import java.util.List;

public interface ProjectService {

    // 프로젝트 생성
    Project createProject(long userId, String projectName);

    // 프로젝트 ID로 프로젝트 조회
    Project getProject(long projectId);

    // 프로젝트 이름으로 프로젝트 조회
    Project getProject(String projectName);

    // 유저가 속한 프로젝트 목록 조회
    List<Project> getProjects(long userId);
}
