package com.nhnacademy.minidoorayteam9taskapi.repository;

import com.nhnacademy.minidoorayteam9taskapi.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Project getProjectById(long projectId);

    Project getProjectByName(String projectName);

}
