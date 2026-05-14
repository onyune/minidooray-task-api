package com.nhnacademy.minidoorayteam9taskapi.exception;

public class MilestoneNotFoundException extends RuntimeException {
    public MilestoneNotFoundException(Long id) {
        super("Milestone not found: " + id);
    }
}
