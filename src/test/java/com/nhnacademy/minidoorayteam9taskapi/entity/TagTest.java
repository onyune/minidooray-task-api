package com.nhnacademy.minidoorayteam9taskapi.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class TagTest {
    @Test
    void tagBuilderAndGetterTest() {
        Project project = Project.builder().name("P").build();
        Tag tag = Tag.builder()
                .name("Tag1")
                .project(project)
                .build();

        assertThat(tag.getName()).isEqualTo("Tag1");
        assertThat(tag.getProject()).isEqualTo(project);
    }

    @Test
    void tagSetterTest() {
        Tag tag = Tag.builder().name("Tag").build();
        Project project = Project.builder().name("P").build();

        tag.setName("Tag_new");
        tag.setProject(project);

        assertThat(tag.getName()).isEqualTo("Tag_new");
        assertThat(tag.getProject()).isEqualTo(project);
    }
}
