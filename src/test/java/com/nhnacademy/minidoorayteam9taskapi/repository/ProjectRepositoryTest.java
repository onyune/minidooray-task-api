package com.nhnacademy.minidoorayteam9taskapi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.nhnacademy.minidoorayteam9taskapi.entity.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.data.jpa.TestEntityManager;

@DataJpaTest
class ProjectRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void getProjectByIdTest() {
        Project project = Project.builder().name("Test Project").build();
        entityManager.persist(project);
        entityManager.flush();

        Project found = projectRepository.getProjectById(project.getId());
        assertThat(found.getName()).isEqualTo("Test Project");
    }

    @Test
    void getProjectByNameTest() {
        Project project = Project.builder().name("Unique Name").build();
        entityManager.persist(project);
        entityManager.flush();

        Project found = projectRepository.getProjectByName("Unique Name");
        assertThat(found.getId()).isEqualTo(project.getId());
    }
}
