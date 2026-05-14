package com.nhnacademy.minidoorayteam9taskapi.service;

import com.nhnacademy.minidoorayteam9taskapi.dto.milestone.MilestoneRequest;
import com.nhnacademy.minidoorayteam9taskapi.dto.milestone.MilestoneResponse;
import java.util.List;

public interface MilestoneService {
    List<MilestoneResponse> getMilestonesByProjectId(Long projectId, Long userId);
    Long createMilestone(Long projectId, MilestoneRequest request, Long userId);
    void updateMilestone(Long projectId, Long milestoneId, MilestoneRequest request, Long userId);
    void deleteMilestone(Long projectId, Long milestoneId, Long userId);
}
