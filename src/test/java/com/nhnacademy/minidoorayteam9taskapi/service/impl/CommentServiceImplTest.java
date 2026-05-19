package com.nhnacademy.minidoorayteam9taskapi.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import com.nhnacademy.minidoorayteam9taskapi.dto.comment.CommentRequest;
import com.nhnacademy.minidoorayteam9taskapi.dto.comment.CommentResponse;
import com.nhnacademy.minidoorayteam9taskapi.entity.Comment;
import com.nhnacademy.minidoorayteam9taskapi.entity.Project;
import com.nhnacademy.minidoorayteam9taskapi.entity.Task;
import com.nhnacademy.minidoorayteam9taskapi.exception.CommentNotFoundException;
import com.nhnacademy.minidoorayteam9taskapi.exception.UnauthorizedAccessException;
import com.nhnacademy.minidoorayteam9taskapi.repository.CommentRepository;
import com.nhnacademy.minidoorayteam9taskapi.repository.ProjectUserRepository;
import com.nhnacademy.minidoorayteam9taskapi.repository.TaskRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private ProjectUserRepository projectUserRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    private Project project;
    private Task task;
    private Long projectId = 1L;
    private Long taskId = 1L;
    private Long userId = 1L;

    @BeforeEach
    void setUp() {
        project = Project.builder().name("P").build();
        task = Task.builder().name("T").content("C").project(project).build();
    }

    @Test
    void getCommentsByTaskId_Success() {
        Comment comment = Comment.builder().content("C1").writerId(userId).task(task).build();
        when(projectUserRepository.existsByProjectIdAndUserId(projectId, userId)).thenReturn(true);
        when(commentRepository.findAllByTaskId(taskId)).thenReturn(List.of(comment));

        List<CommentResponse> result = commentService.getCommentsByTaskId(projectId, taskId, userId);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).content()).isEqualTo("C1");
    }

    @Test
    void createComment_Success() {
        CommentRequest request = new CommentRequest("New Comment");
        when(projectUserRepository.existsByProjectIdAndUserId(projectId, userId)).thenReturn(true);
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(commentRepository.save(any(Comment.class))).thenAnswer(i -> {
            Comment c = i.getArgument(0);
            return c;
        });

        commentService.createComment(projectId, taskId, request, userId);

        verify(commentRepository).save(any(Comment.class));
    }

    @Test
    void updateComment_Success() {
        Comment comment = Comment.builder().content("Old").writerId(userId).task(task).build();
        CommentRequest request = new CommentRequest("Updated");

        when(projectUserRepository.existsByProjectIdAndUserId(projectId, userId)).thenReturn(true);
        when(commentRepository.findById(anyLong())).thenReturn(Optional.of(comment));

        commentService.updateComment(projectId, 1L, request, userId);

        assertThat(comment.getContent()).isEqualTo("Updated");
    }

    @Test
    void updateComment_Unauthorized() {
        Comment comment = Comment.builder().content("Old").writerId(2L).task(task).build();
        CommentRequest request = new CommentRequest("Updated");

        when(projectUserRepository.existsByProjectIdAndUserId(projectId, userId)).thenReturn(true);
        when(commentRepository.findById(anyLong())).thenReturn(Optional.of(comment));

        assertThatThrownBy(() -> commentService.updateComment(projectId, 1L, request, userId))
            .isInstanceOf(UnauthorizedAccessException.class);
    }

    @Test
    void deleteComment_Success() {
        Comment comment = Comment.builder().content("Old").writerId(userId).task(task).build();
        when(projectUserRepository.existsByProjectIdAndUserId(projectId, userId)).thenReturn(true);
        when(commentRepository.findById(anyLong())).thenReturn(Optional.of(comment));

        commentService.deleteComment(projectId, 1L, userId);

        verify(commentRepository).delete(comment);
    }

    @Test
    void getComment_NotFound() {
        when(projectUserRepository.existsByProjectIdAndUserId(projectId, userId)).thenReturn(true);
        when(commentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> commentService.getComment(projectId, 1L, userId))
            .isInstanceOf(CommentNotFoundException.class);
    }
}
