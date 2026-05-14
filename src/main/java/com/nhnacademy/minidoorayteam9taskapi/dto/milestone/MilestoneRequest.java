package com.nhnacademy.minidoorayteam9taskapi.dto.milestone;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record MilestoneRequest(
        @NotBlank @Size(min = 1, max = 100)  String name,
        LocalDate startAt,
        LocalDate endAt
) {
}
