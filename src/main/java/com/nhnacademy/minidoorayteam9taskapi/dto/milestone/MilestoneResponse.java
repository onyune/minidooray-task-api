package com.nhnacademy.minidoorayteam9taskapi.dto.milestone;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record MilestoneResponse(
        @NotNull Long id,
        @NotBlank @Size(min = 1, max = 100) String name,
        LocalDate startAt,
        LocalDate endAt
) {
}
