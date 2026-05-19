package com.nhnacademy.minidoorayteam9taskapi.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class TaskTagTest {
    @Test
    void taskTagBuilderAndGetterTest() {
        Task task = Task.builder().name("T").build();
        Tag tag = Tag.builder().name("Tag").build();
        TaskTag taskTag = TaskTag.builder()
                .task(task)
                .tag(tag)
                .build();

        assertThat(taskTag.getTask()).isEqualTo(task);
        assertThat(taskTag.getTag()).isEqualTo(tag);
    }

    @Test
    void taskTagSetterTest() {
        TaskTag taskTag = TaskTag.builder().build();
        Task task = Task.builder().name("T").build();
        Tag tag = Tag.builder().name("Tag").build();

        taskTag.setTask(task);
        taskTag.setTag(tag);

        assertThat(taskTag.getTask()).isEqualTo(task);
        assertThat(taskTag.getTag()).isEqualTo(tag);
    }
}
