package com.nhnacademy.minidoorayteam9taskapi.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class MilestoneTest {
    @Test
    void milestoneBuilderAndGetterTest() {
        Project project = Project.builder().name("P").build();
        LocalDate start = LocalDate.now();
        LocalDate end = start.plusDays(7);
        Milestone milestone = Milestone.builder()
                .name("M1")
                .startAt(start)
                .endAt(end)
                .project(project)
                .build();

        assertThat(milestone.getName()).isEqualTo("M1");
        assertThat(milestone.getStartAt()).isEqualTo(start);
        assertThat(milestone.getEndAt()).isEqualTo(end);
        assertThat(milestone.getProject()).isEqualTo(project);
    }

    @Test
    void milestoneSetterTest() {
        Milestone milestone = Milestone.builder().name("M").build();
        Project project = Project.builder().name("P").build();
        LocalDate start = LocalDate.now();
        LocalDate end = start.plusDays(7);

        milestone.setName("M_new");
        milestone.setStartAt(start);
        milestone.setEndAt(end);
        milestone.setProject(project);

        assertThat(milestone.getName()).isEqualTo("M_new");
        assertThat(milestone.getStartAt()).isEqualTo(start);
        assertThat(milestone.getEndAt()).isEqualTo(end);
        assertThat(milestone.getProject()).isEqualTo(project);
    }
}
