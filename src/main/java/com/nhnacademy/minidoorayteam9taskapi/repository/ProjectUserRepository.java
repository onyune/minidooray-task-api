package com.nhnacademy.minidoorayteam9taskapi.repository;

import com.nhnacademy.minidoorayteam9taskapi.entity.ProjectUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectUserRepository extends JpaRepository<ProjectUser, Long> {
    boolean existsByIdAndUserId(long projectId, long userId);
    
    ProjectUser getProjectUserByProjectIdAndUserId(long projectId, long userId);
    
    List<ProjectUser> findAllByProjectId(long projectId);

    List<ProjectUser> findAllByUserId(long userId);
    
    boolean existsByProjectIdAndUserId(Long projectId, Long userId);

}
