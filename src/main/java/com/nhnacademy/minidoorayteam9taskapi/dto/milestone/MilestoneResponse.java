package com.nhnacademy.minidoorayteam9taskapi.dto.milestone;

import java.time.LocalDate;

public record MilestoneResponse(
        Long id,
        String name,
        LocalDate startAt,
        LocalDate endAt
) {
}
