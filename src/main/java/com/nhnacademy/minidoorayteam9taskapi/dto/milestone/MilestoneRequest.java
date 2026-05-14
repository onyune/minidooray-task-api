package com.nhnacademy.minidoorayteam9taskapi.dto.milestone;

import java.time.LocalDate;

public record MilestoneRequest(
        String name,
        LocalDate startAt,
        LocalDate endAt
) {
}
