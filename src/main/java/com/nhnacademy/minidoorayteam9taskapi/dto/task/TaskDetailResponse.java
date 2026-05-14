package com.nhnacademy.minidoorayteam9taskapi.dto.task;

import com.nhnacademy.minidoorayteam9taskapi.dto.TagResponse;
import java.util.List;

public record TaskDetailResponse(
    Long taskId,
    String taskName,
    String taskContent,
    Long milestoneId,
    String milestoneName,
    List<TagResponse> tags
) {
}
