package com.nhnacademy.minidoorayteam9taskapi.service;

import com.nhnacademy.minidoorayteam9taskapi.dto.comment.CommentRequest;
import com.nhnacademy.minidoorayteam9taskapi.dto.comment.CommentResponse;
import java.util.List;

public interface CommentService {
    // task의 코멘트 리스트
    List<CommentResponse> getCommentsByTaskId(Long projectId, Long taskId, Long userId);

    // 코멘트 디테일
    CommentResponse getComment(Long projectId, Long commentId, Long userId);

    // 코멘트 생성
    Long createComment(Long projectId, Long taskId, CommentRequest commentRequest, Long writerId);

    // 코멘트 수정
    void updateComment(Long projectId, Long commentId, CommentRequest commentRequest, Long writerId);

    // 코멘트 삭제
    void deleteComment(Long projectId, Long commentId, Long writerId);

}
