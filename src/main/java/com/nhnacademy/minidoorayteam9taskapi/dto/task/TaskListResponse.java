package com.nhnacademy.minidoorayteam9taskapi.dto.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TaskListResponse(
    @NotNull Long taskId,
    @NotBlank @Size(min = 1, max = 100) String taskName
) {
}
