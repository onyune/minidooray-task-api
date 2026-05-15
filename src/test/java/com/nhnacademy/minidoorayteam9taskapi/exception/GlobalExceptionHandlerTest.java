package com.nhnacademy.minidoorayteam9taskapi.exception;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void handleCommentNotFoundTest() {
        CommentNotFoundException ex = new CommentNotFoundException(1L);
        ResponseEntity<Map<String, String>> response = handler.handleCommentNotFound(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).containsEntry("error", "Not Found");
        assertThat(response.getBody()).containsEntry("message", ex.getMessage());
    }

    @Test
    void handleMilestoneNotFoundTest() {
        MilestoneNotFoundException ex = new MilestoneNotFoundException(1L);
        ResponseEntity<Map<String, String>> response = handler.handleMilestoneNotFound(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).containsEntry("error", "Not Found");
        assertThat(response.getBody()).containsEntry("message", ex.getMessage());
    }

    @Test
    void handleUnauthorizedAccessTest() {
        UnauthorizedAccessException ex = new UnauthorizedAccessException("Unauthorized");
        ResponseEntity<Map<String, String>> response = handler.handleUnauthorizedAccess(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(response.getBody()).containsEntry("error", "Forbidden");
        assertThat(response.getBody()).containsEntry("message", ex.getMessage());
    }

    @Test
    void handleRuntimeExceptionTest() {
        RuntimeException ex = new RuntimeException("Generic error");
        ResponseEntity<Map<String, String>> response = handler.handleRuntimeException(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).containsEntry("error", "Internal Server Error");
        assertThat(response.getBody()).containsEntry("message", ex.getMessage());
    }
}
