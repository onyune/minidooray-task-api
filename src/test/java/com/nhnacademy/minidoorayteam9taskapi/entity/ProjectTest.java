package com.nhnacademy.minidoorayteam9taskapi.entity;

import static org.assertj.core.api.Assertions.assertThat;

import com.nhnacademy.minidoorayteam9taskapi.entity.enums.ProjectStatus;
import org.junit.jupiter.api.Test;

class ProjectTest {

    @Test
    void projectBuilderAndGetterTest() {
        Project project = Project.builder()
                .name("Test Project")
                .build();

        assertThat(project.getName()).isEqualTo("Test Project");
        assertThat(project.getStatus()).isEqualTo(ProjectStatus.ACTIVE);
    }

    @Test
    void projectSetterTest() {
        Project project = Project.builder()
                .name("Old Name")
                .build();

        project.setName("New Name");
        project.setStatus(ProjectStatus.CLOSED);

        assertThat(project.getName()).isEqualTo("New Name");
        assertThat(project.getStatus()).isEqualTo(ProjectStatus.CLOSED);
    }
}
