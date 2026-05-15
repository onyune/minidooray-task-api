package com.nhnacademy.minidoorayteam9taskapi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.nhnacademy.minidoorayteam9taskapi.entity.Project;
import com.nhnacademy.minidoorayteam9taskapi.entity.ProjectUser;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;


@DataJpaTest
class ProjectUserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProjectUserRepository projectUserRepository;

    @Test
    void existsByIdAndUserIdTest() {
        Project project = Project.builder().name("P").build();
        entityManager.persist(project);
        
        ProjectUser projectUser = ProjectUser.builder()
                .userId(1L)
                .isAdmin(true)
                .project(project)
                .build();
        entityManager.persist(projectUser);
        entityManager.flush();

        boolean exists = projectUserRepository.existsByIdAndUserId(projectUser.getId(), 1L);
        assertThat(exists).isTrue();
    }

    @Test
    void getProjectUserByProjectIdAndUserIdTest() {
        Project project = Project.builder().name("P").build();
        entityManager.persist(project);

        ProjectUser projectUser = ProjectUser.builder()
                .userId(1L)
                .isAdmin(true)
                .project(project)
                .build();
        entityManager.persist(projectUser);
        entityManager.flush();

        ProjectUser found = projectUserRepository.getProjectUserByProjectIdAndUserId(project.getId(), 1L);
        assertThat(found.getId()).isEqualTo(projectUser.getId());
    }

    @Test
    void findAllByProjectIdTest() {
        Project project = Project.builder().name("P").build();
        entityManager.persist(project);

        ProjectUser user1 = ProjectUser.builder().userId(1L).project(project).build();
        ProjectUser user2 = ProjectUser.builder().userId(2L).project(project).build();
        entityManager.persist(user1);
        entityManager.persist(user2);
        entityManager.flush();

        List<ProjectUser> users = projectUserRepository.findAllByProjectId(project.getId());
        assertThat(users).hasSize(2);
    }

    @Test
    void findAllByUserIdTest() {
        Project p1 = Project.builder().name("P1").build();
        Project p2 = Project.builder().name("P2").build();
        entityManager.persist(p1);
        entityManager.persist(p2);

        ProjectUser u1 = ProjectUser.builder().userId(100L).project(p1).build();
        ProjectUser u2 = ProjectUser.builder().userId(100L).project(p2).build();
        entityManager.persist(u1);
        entityManager.persist(u2);
        entityManager.flush();

        List<ProjectUser> userProjects = projectUserRepository.findAllByUserId(100L);
        assertThat(userProjects).hasSize(2);
    }

    @Test
    void existsByProjectIdAndUserIdTest() {
        Project project = Project.builder().name("P").build();
        entityManager.persist(project);

        ProjectUser projectUser = ProjectUser.builder()
                .userId(1L)
                .isAdmin(true)
                .project(project)
                .build();
        entityManager.persist(projectUser);
        entityManager.flush();

        boolean exists = projectUserRepository.existsByProjectIdAndUserId(project.getId(), 1L);
        assertThat(exists).isTrue();
    }
}
