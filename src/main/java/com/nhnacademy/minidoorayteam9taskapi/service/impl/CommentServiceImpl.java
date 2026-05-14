package com.nhnacademy.minidoorayteam9taskapi.service.impl;

import com.nhnacademy.minidoorayteam9taskapi.dto.comment.CommentRequest;
import com.nhnacademy.minidoorayteam9taskapi.dto.comment.CommentResponse;
import com.nhnacademy.minidoorayteam9taskapi.entity.Comment;
import com.nhnacademy.minidoorayteam9taskapi.entity.Task;
import com.nhnacademy.minidoorayteam9taskapi.exception.CommentNotFoundException;
import com.nhnacademy.minidoorayteam9taskapi.exception.UnauthorizedAccessException;
import com.nhnacademy.minidoorayteam9taskapi.repository.CommentRepository;
import com.nhnacademy.minidoorayteam9taskapi.repository.ProjectUserRepository;
import com.nhnacademy.minidoorayteam9taskapi.repository.TaskRepository;
import com.nhnacademy.minidoorayteam9taskapi.service.CommentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final ProjectUserRepository projectUserRepository;

    private void validateProjectMember(Long projectId, Long userId) {
        if (!projectUserRepository.existsByProjectIdAndUserId(projectId, userId)) {
            throw new UnauthorizedAccessException("User " + userId + " is not a member of project " + projectId);
        }
    }

    @Override
    public List<CommentResponse> getCommentsByTaskId(Long projectId, Long taskId, Long userId) {
        validateProjectMember(projectId, userId);
        return commentRepository.findAllByTaskId(taskId).stream().map(comment ->
                new CommentResponse(comment.getId(), comment.getContent(), comment.getWriterId()))
                .toList();
    }

    @Override
    public CommentResponse getComment(Long projectId, Long commentId, Long userId) {
        validateProjectMember(projectId, userId);
        return commentRepository.findById(commentId).map(comment ->
                        new CommentResponse(comment.getId(), comment.getContent(), comment.getWriterId()))
                .orElseThrow(()->new CommentNotFoundException(commentId));
    }

    @Override
    @Transactional
    public Long createComment(Long projectId, Long taskId, CommentRequest commentRequest, Long writerId) {
        validateProjectMember(projectId, writerId);
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        Comment comment = Comment.builder()
                .content(commentRequest.content())
                .writerId(writerId)
                .task(task)
                .build();

        return commentRepository.save(comment).getId();
    }

    @Override
    @Transactional
    public void updateComment(Long projectId, Long commentId, CommentRequest commentRequest, Long writerId) {
        validateProjectMember(projectId, writerId);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));

        if (!comment.getWriterId().equals(writerId)) {
            throw new UnauthorizedAccessException("You are not the writer of this comment.");
        }

        comment.setContent(commentRequest.content());
        commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void deleteComment(Long projectId, Long commentId, Long writerId) {
        validateProjectMember(projectId, writerId);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));

        if (!comment.getWriterId().equals(writerId)) {
            throw new UnauthorizedAccessException("You are not the writer of this comment.");
        }

        commentRepository.delete(comment);
    }
}
