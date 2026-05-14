package com.nhnacademy.minidoorayteam9taskapi.dto.comment;

public record CommentResponse(
        Long commentId,
        String content,
        Long writerId
) {
}
