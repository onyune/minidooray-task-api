package com.nhnacademy.minidoorayteam9taskapi.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class TaskTest {
    @Test
    void taskBuilderAndGetterTest() {
        Project project = Project.builder().name("P").build();
        Milestone milestone = Milestone.builder().name("M").build();
        Task task = Task.builder()
                .name("T1")
                .content("C1")
                .project(project)
                .milestone(milestone)
                .build();

        assertThat(task.getName()).isEqualTo("T1");
        assertThat(task.getContent()).isEqualTo("C1");
        assertThat(task.getProject()).isEqualTo(project);
        assertThat(task.getMilestone()).isEqualTo(milestone);
    }

    @Test
    void taskSetterTest() {
        Task task = Task.builder().name("T").build();
        Project project = Project.builder().name("P").build();
        Milestone milestone = Milestone.builder().name("M").build();

        task.setName("T_new");
        task.setContent("C_new");
        task.setProject(project);
        task.setMilestone(milestone);

        assertThat(task.getName()).isEqualTo("T_new");
        assertThat(task.getContent()).isEqualTo("C_new");
        assertThat(task.getProject()).isEqualTo(project);
        assertThat(task.getMilestone()).isEqualTo(milestone);
    }
}
