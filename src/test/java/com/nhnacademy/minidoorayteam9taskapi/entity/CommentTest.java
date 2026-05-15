package com.nhnacademy.minidoorayteam9taskapi.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class CommentTest {
    @Test
    void commentBuilderAndGetterTest() {
        Task task = Task.builder().name("T").build();
        Comment comment = Comment.builder()
                .content("C1")
                .writerId(1L)
                .task(task)
                .build();

        assertThat(comment.getContent()).isEqualTo("C1");
        assertThat(comment.getWriterId()).isEqualTo(1L);
        assertThat(comment.getTask()).isEqualTo(task);
    }

    @Test
    void commentSetterTest() {
        Comment comment = Comment.builder().content("C").build();
        Task task = Task.builder().name("T").build();

        comment.setContent("C_new");
        comment.setWriterId(2L);
        comment.setTask(task);

        assertThat(comment.getContent()).isEqualTo("C_new");
        assertThat(comment.getWriterId()).isEqualTo(2L);
        assertThat(comment.getTask()).isEqualTo(task);
    }
}
