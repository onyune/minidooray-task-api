package com.nhnacademy.minidoorayteam9taskapi.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ExceptionTests {

    @Test
    void commentNotFoundExceptionTest() {
        CommentNotFoundException exception = new CommentNotFoundException(1L);
        assertThat(exception.getMessage()).contains("1");
    }

    @Test
    void milestoneNotFoundExceptionTest() {
        MilestoneNotFoundException exception = new MilestoneNotFoundException(1L);
        assertThat(exception.getMessage()).contains("1");
    }

    @Test
    void projectNotFoundExceptionTest() {
        ProjectNotFoundException exception = new ProjectNotFoundException("Project not found");
        assertThat(exception.getMessage()).isEqualTo("Project not found");
    }

    @Test
    void projectUserAlreadyExistExceptionTest() {
        ProjectUserAlreadyExistException exception = new ProjectUserAlreadyExistException("User already exists");
        assertThat(exception.getMessage()).isEqualTo("User already exists");
    }

    @Test
    void projectUserNotFoundExceptionTest() {
        ProjectUserNotFoundException exception = new ProjectUserNotFoundException("User not found");
        assertThat(exception.getMessage()).isEqualTo("User not found");
    }

    @Test
    void unauthorizedAccessExceptionTest() {
        UnauthorizedAccessException exception = new UnauthorizedAccessException("Unauthorized");
        assertThat(exception.getMessage()).isEqualTo("Unauthorized");
    }
}
