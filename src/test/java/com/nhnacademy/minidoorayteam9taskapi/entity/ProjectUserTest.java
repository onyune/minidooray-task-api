package com.nhnacademy.minidoorayteam9taskapi.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ProjectUserTest {

    @Test
    void projectUserBuilderAndGetterTest() {
        Project project = Project.builder().name("Project").build();
        ProjectUser projectUser = ProjectUser.builder()
                .userId(1L)
                .isAdmin(true)
                .project(project)
                .build();

        assertThat(projectUser.getUserId()).isEqualTo(1L);
        assertThat(projectUser.isAdmin()).isTrue();
        assertThat(projectUser.getProject()).isEqualTo(project);
    }

    @Test
    void projectUserSetterTest() {
        Project project1 = Project.builder().name("P1").build();
        Project project2 = Project.builder().name("P2").build();
        ProjectUser projectUser = ProjectUser.builder()
                .userId(1L)
                .isAdmin(false)
                .project(project1)
                .build();

        projectUser.setUserId(2L);
        projectUser.setAdmin(true);
        projectUser.setProject(project2);

        assertThat(projectUser.getUserId()).isEqualTo(2L);
        assertThat(projectUser.isAdmin()).isTrue();
        assertThat(projectUser.getProject()).isEqualTo(project2);
    }
}
