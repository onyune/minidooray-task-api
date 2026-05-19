package com.nhnacademy.minidoorayteam9taskapi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.nhnacademy.minidoorayteam9taskapi.entity.Project;
import com.nhnacademy.minidoorayteam9taskapi.entity.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void findAllByProjectIdTest() {
        Project project = Project.builder().name("P").build();
        entityManager.persist(project);

        Task task1 = Task.builder().name("T1").content("C1").project(project).build();
        Task task2 = Task.builder().name("T2").content("C2").project(project).build();
        entityManager.persist(task1);
        entityManager.persist(task2);
        entityManager.flush();

        Page<Task> tasks = taskRepository.findAllByProjectId(project.getId(), PageRequest.of(0, 10));
        assertThat(tasks.getContent()).hasSize(2);
    }
}
