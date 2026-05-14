package com.nhnacademy.minidoorayteam9taskapi.repository;

import com.nhnacademy.minidoorayteam9taskapi.entity.Task;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findAllByProjectId(Long projectId, Pageable pageable);
    List<Task> findAllByMilestoneId(Long milestoneId);
}
