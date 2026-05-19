package com.nhnacademy.minidoorayteam9taskapi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.nhnacademy.minidoorayteam9taskapi.entity.Project;
import com.nhnacademy.minidoorayteam9taskapi.entity.Tag;
import com.nhnacademy.minidoorayteam9taskapi.entity.Task;
import com.nhnacademy.minidoorayteam9taskapi.entity.TaskTag;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;


@DataJpaTest
class TaskTagRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TaskTagRepository taskTagRepository;

    @Test
    void findAllByTaskIdTest() {
        Project project = Project.builder().name("P").build();
        entityManager.persist(project);
        Task task = Task.builder().name("T").content("C").project(project).build();
        entityManager.persist(task);
        Tag tag = Tag.builder().name("Tag").project(project).build();
        entityManager.persist(tag);

        TaskTag taskTag = TaskTag.builder().task(task).tag(tag).build();
        entityManager.persist(taskTag);
        entityManager.flush();

        List<TaskTag> taskTags = taskTagRepository.findAllByTaskId(task.getId());
        assertThat(taskTags).hasSize(1);
    }

    @Test
    void deleteByTaskIdTest() {
        Project project = Project.builder().name("P").build();
        entityManager.persist(project);
        Task task = Task.builder().name("T").content("C").project(project).build();
        entityManager.persist(task);
        Tag tag = Tag.builder().name("Tag").project(project).build();
        entityManager.persist(tag);

        TaskTag taskTag = TaskTag.builder().task(task).tag(tag).build();
        entityManager.persist(taskTag);
        entityManager.flush();

        taskTagRepository.deleteByTaskId(task.getId());
        entityManager.flush();
        entityManager.clear();

        List<TaskTag> taskTags = taskTagRepository.findAllByTaskId(task.getId());
        assertThat(taskTags).isEmpty();
    }
}
