package com.nhnacademy.minidoorayteam9taskapi.controller;

import com.nhnacademy.minidoorayteam9taskapi.dto.milestone.MilestoneRequest;
import com.nhnacademy.minidoorayteam9taskapi.dto.milestone.MilestoneResponse;
import com.nhnacademy.minidoorayteam9taskapi.service.MilestoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects/{projectId}/milestones")
@RequiredArgsConstructor
public class MilestoneController {
    private final MilestoneService milestoneService;

    @GetMapping
    public List<MilestoneResponse> getMilestones(@PathVariable Long projectId,
                                                 @RequestHeader("X-USER-ID") Long userId) {
        return milestoneService.getMilestonesByProjectId(projectId, userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long createMilestone(@PathVariable Long projectId,
                                 @RequestBody MilestoneRequest request,
                                 @RequestHeader("X-USER-ID") Long userId) {
        return milestoneService.createMilestone(projectId, request, userId);
    }

    @PutMapping("/{milestoneId}")
    public void updateMilestone(@PathVariable Long projectId,
                                 @PathVariable Long milestoneId,
                                 @RequestBody MilestoneRequest request,
                                 @RequestHeader("X-USER-ID") Long userId) {
        milestoneService.updateMilestone(projectId, milestoneId, request, userId);
    }

    @DeleteMapping("/{milestoneId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMilestone(@PathVariable Long projectId,
                                 @PathVariable Long milestoneId,
                                 @RequestHeader("X-USER-ID") Long userId) {
        milestoneService.deleteMilestone(projectId, milestoneId, userId);
    }
}
