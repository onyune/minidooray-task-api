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

import com.nhnacademy.minidoorayteam9taskapi.dto.comment.CommentRequest;
import com.nhnacademy.minidoorayteam9taskapi.dto.comment.CommentResponse;
import com.nhnacademy.minidoorayteam9taskapi.service.CommentService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

@WebMvcTest(CommentController.class)
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CommentService commentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getComments() throws Exception {
        CommentResponse response = new CommentResponse(1L, "C", 1L);
        when(commentService.getCommentsByTaskId(anyLong(), anyLong(), anyLong())).thenReturn(List.of(response));

        mockMvc.perform(get("/projects/1/tasks/1/comments")
                .header("X-USER-ID", 1L))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].content").value("C"));
    }

    @Test
    void createComment() throws Exception {
        CommentRequest request = new CommentRequest("C");
        when(commentService.createComment(anyLong(), anyLong(), any(CommentRequest.class), anyLong())).thenReturn(1L);

        mockMvc.perform(post("/projects/1/tasks/1/comments")
                .header("X-USER-ID", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(content().string("1"));
    }

    @Test
    void updateComment() throws Exception {
        CommentRequest request = new CommentRequest("C");

        mockMvc.perform(put("/projects/1/tasks/1/comments/1")
                .header("X-USER-ID", 1L)
                .param("projectId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk());
    }

    @Test
    void deleteComment() throws Exception {
        mockMvc.perform(delete("/projects/1/tasks/1/comments/1")
                .header("X-USER-ID", 1L))
            .andExpect(status().isNoContent());
    }
}
