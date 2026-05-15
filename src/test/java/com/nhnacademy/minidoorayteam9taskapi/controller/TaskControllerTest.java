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

import com.nhnacademy.minidoorayteam9taskapi.dto.task.TaskDetailResponse;
import com.nhnacademy.minidoorayteam9taskapi.dto.task.TaskListResponse;
import com.nhnacademy.minidoorayteam9taskapi.dto.task.TaskRequest;
import com.nhnacademy.minidoorayteam9taskapi.service.TaskService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getTasks() throws Exception {
        TaskListResponse response = new TaskListResponse(1L, "Task");
        when(taskService.getTasksByProjectId(anyLong(), anyLong(), any())).thenReturn(new PageImpl<>(List.of(response)));

        mockMvc.perform(get("/projects/1/tasks")
                .header("X-USER-ID", 1L)
                .param("page", "0")
                .param("size", "10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].taskName").value("Task"));
    }

    @Test
    void getTask() throws Exception {
        TaskDetailResponse response = new TaskDetailResponse(1L, "Task", "Content", null, null, List.of());
        when(taskService.getTask(anyLong(), anyLong(), anyLong())).thenReturn(response);

        mockMvc.perform(get("/projects/1/tasks/1")
                .header("X-USER-ID", 1L))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.taskName").value("Task"));
    }

    @Test
    void createTask() throws Exception {
        TaskRequest request = new TaskRequest("Task", "Content", null, null);
        when(taskService.createTask(anyLong(), any(TaskRequest.class), anyLong())).thenReturn(1L);

        mockMvc.perform(post("/projects/1/tasks")
                .header("X-USER-ID", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(content().string("1"));
    }

    @Test
    void updateTask() throws Exception {
        TaskRequest request = new TaskRequest("Task", "Content", null, null);

        mockMvc.perform(put("/projects/1/tasks/1")
                .header("X-USER-ID", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk());
    }

    @Test
    void deleteTask() throws Exception {
        mockMvc.perform(delete("/projects/1/tasks/1")
                .header("X-USER-ID", 1L))
            .andExpect(status().isNoContent());
    }
}
