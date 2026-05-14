package com.nhnacademy.minidoorayteam9taskapi.repository;

import com.nhnacademy.minidoorayteam9taskapi.entity.TaskTag;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskTagRepository extends JpaRepository<TaskTag, Long> {
    List<TaskTag> findAllByTaskId(Long taskId);
    void deleteByTaskId(Long taskId);
}
