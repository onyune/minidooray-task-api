package com.nhnacademy.minidoorayteam9taskapi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.nhnacademy.minidoorayteam9taskapi.entity.Milestone;
import com.nhnacademy.minidoorayteam9taskapi.entity.Project;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class MilestoneRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MilestoneRepository milestoneRepository;

    @Test
    void findAllByProjectIdTest() {
        Project project = Project.builder().name("P").build();
        entityManager.persist(project);

        Milestone milestone = Milestone.builder().name("M1").project(project).build();
        entityManager.persist(milestone);
        entityManager.flush();

        List<Milestone> milestones = milestoneRepository.findAllByProjectId(project.getId());
        assertThat(milestones).hasSize(1);
    }
}
