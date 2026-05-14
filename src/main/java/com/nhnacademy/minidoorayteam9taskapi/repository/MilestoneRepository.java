package com.nhnacademy.minidoorayteam9taskapi.repository;

import com.nhnacademy.minidoorayteam9taskapi.entity.Milestone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MilestoneRepository extends JpaRepository<Milestone, Long> {
    List<Milestone> findAllByProjectId(Long projectId);
}
