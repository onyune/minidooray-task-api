package com.nhnacademy.minidoorayteam9taskapi.dto.task;

import java.util.List;

public record TaskRequest(
    String taskName,
    String taskContent,
    Long milestoneId,
    List<Long> tagIds
) {
}
