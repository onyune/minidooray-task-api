package com.nhnacademy.minidoorayteam9taskapi.controller;

import com.nhnacademy.minidoorayteam9taskapi.dto.comment.CommentRequest;
import com.nhnacademy.minidoorayteam9taskapi.dto.comment.CommentResponse;
import com.nhnacademy.minidoorayteam9taskapi.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects/{projectId}/tasks/{taskId}/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping
    public List<CommentResponse> getComments(@PathVariable Long projectId,
                                             @PathVariable Long taskId,
                                             @RequestHeader("X-USER-ID") Long userId) {
        return commentService.getCommentsByTaskId(projectId, taskId, userId);
    }

    @GetMapping("/{commentId}")
    public CommentResponse getComment(@PathVariable Long projectId,
                                       @PathVariable Long taskId,
                                       @PathVariable Long commentId,
                                       @RequestHeader("X-USER-ID") Long userId) {
        return commentService.getComment(projectId, commentId, userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long createComment(@PathVariable Long projectId,
                               @PathVariable Long taskId,
                               @RequestBody CommentRequest request,
                               @RequestHeader("X-USER-ID") Long userId) {
        return commentService.createComment(projectId, taskId, request, userId);
    }

    @PutMapping("/{commentId}")
    public void updateComment(@PathVariable Long projectId,
                               @PathVariable Long taskId,
                               @PathVariable Long commentId,
                               @RequestBody CommentRequest request,
                               @RequestHeader("X-USER-ID") Long userId) {
        commentService.updateComment(projectId, commentId, request, userId);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable Long projectId,
                               @PathVariable Long taskId,
                               @PathVariable Long commentId,
                               @RequestHeader("X-USER-ID") Long userId) {
        commentService.deleteComment(projectId, commentId, userId);
    }
}
