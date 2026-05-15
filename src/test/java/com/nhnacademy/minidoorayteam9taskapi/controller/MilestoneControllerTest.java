package com.nhnacademy.minidoorayteam9taskapi.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nhnacademy.minidoorayteam9taskapi.dto.milestone.MilestoneRequest;
import com.nhnacademy.minidoorayteam9taskapi.dto.milestone.MilestoneResponse;
import com.nhnacademy.minidoorayteam9taskapi.service.MilestoneService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

@WebMvcTest(MilestoneController.class)
class MilestoneControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MilestoneService milestoneService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getMilestones() throws Exception {
        MilestoneResponse response = new MilestoneResponse(1L, "M", null, null);
        when(milestoneService.getMilestonesByProjectId(anyLong(), anyLong())).thenReturn(List.of(response));

        mockMvc.perform(get("/projects/1/milestones")
                .header("X-USER-ID", 1L))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name").value("M"));
    }

    @Test
    void createMilestone() throws Exception {
        MilestoneRequest request = new MilestoneRequest("M", null, null);
        when(milestoneService.createMilestone(anyLong(), any(MilestoneRequest.class), anyLong())).thenReturn(1L);

        mockMvc.perform(post("/projects/1/milestones")
                .header("X-USER-ID", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(content().string("1"));
    }

    @Test
    void updateMilestone() throws Exception {
        MilestoneRequest request = new MilestoneRequest("M", null, null);

        mockMvc.perform(put("/projects/1/milestones/1")
                .header("X-USER-ID", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk());
    }

    @Test
    void deleteMilestone() throws Exception {
        mockMvc.perform(delete("/projects/1/milestones/1")
                .header("X-USER-ID", 1L))
            .andExpect(status().isNoContent());
    }
}
