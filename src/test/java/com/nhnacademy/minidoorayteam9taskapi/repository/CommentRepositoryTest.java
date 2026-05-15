package com.nhnacademy.minidoorayteam9taskapi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.nhnacademy.minidoorayteam9taskapi.entity.Comment;
import com.nhnacademy.minidoorayteam9taskapi.entity.Project;
import com.nhnacademy.minidoorayteam9taskapi.entity.Task;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    void findAllByTaskIdTest() {
        Project project = Project.builder().name("P").build();
        entityManager.persist(project);
        Task task = Task.builder().name("T").content("C").project(project).build();
        entityManager.persist(task);

        Comment comment = Comment.builder().content("C1").writerId(1L).task(task).build();
        entityManager.persist(comment);
        entityManager.flush();

        List<Comment> comments = commentRepository.findAllByTaskId(task.getId());
        assertThat(comments).hasSize(1);
    }

    @Test
    void deleteByTaskIdTest() {
        Project project = Project.builder().name("P").build();
        entityManager.persist(project);
        Task task = Task.builder().name("T").content("C").project(project).build();
        entityManager.persist(task);

        Comment comment = Comment.builder().content("C1").writerId(1L).task(task).build();
        entityManager.persist(comment);
        entityManager.flush();

        commentRepository.deleteByTaskId(task.getId());
        entityManager.flush();
        entityManager.clear();

        List<Comment> comments = commentRepository.findAllByTaskId(task.getId());
        assertThat(comments).isEmpty();
    }
}
