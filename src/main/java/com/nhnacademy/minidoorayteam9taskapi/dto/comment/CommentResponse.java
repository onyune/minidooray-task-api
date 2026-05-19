package com.nhnacademy.minidoorayteam9taskapi.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentResponse(
        @NotNull Long commentId,
        @NotBlank String content,
        @NotNull Long writerId
) {
}
