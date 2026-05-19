package com.nhnacademy.minidoorayteam9taskapi.dto.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

public record TaskRequest(
    @NotBlank @Size(min = 1, max = 100) String taskName,
    @NotBlank String taskContent,
    Long milestoneId,
    List<Long> tagIds
) {
}
