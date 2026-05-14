package com.nhnacademy.minidoorayteam9taskapi.service;

import com.nhnacademy.minidoorayteam9taskapi.entity.Project;


public interface ProjectService {

    // 프로젝트 생성
    Project createProject(long userId, String projectName);

    // 프로젝트 ID로 프로젝트 조회
    Project getProject(long projectId);

    // 프로젝트 이름으로 프로젝트 조회
    Project getProject(String projectName);

}
