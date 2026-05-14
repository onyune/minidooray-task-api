package com.nhnacademy.minidoorayteam9taskapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TagResponse(
        @NotNull Long tagId,
        @NotBlank String tagName
) {}