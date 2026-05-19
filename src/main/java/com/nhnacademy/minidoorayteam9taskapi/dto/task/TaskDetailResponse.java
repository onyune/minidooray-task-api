package com.nhnacademy.minidoorayteam9taskapi.dto.task;

import com.nhnacademy.minidoorayteam9taskapi.dto.TagResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public record TaskDetailResponse(
    @NotNull Long taskId,
    @NotBlank @Size(min = 1, max = 100) String taskName,
    @NotBlank String taskContent,
    Long milestoneId,
    String milestoneName,
    List<TagResponse> tags
) {
}
