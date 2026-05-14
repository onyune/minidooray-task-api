package com.nhnacademy.minidoorayteam9taskapi.dto.comment;

import jakarta.validation.constraints.NotBlank;

public record CommentRequest(
        @NotBlank String content
) {
}
