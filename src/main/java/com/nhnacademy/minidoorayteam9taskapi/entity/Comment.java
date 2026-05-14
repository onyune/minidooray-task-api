package com.nhnacademy.minidoorayteam9taskapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comments")
@Getter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", nullable = false)
    private Long id;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    @Setter
    @NotBlank
    private String content;

    @Column(name = "writer_id", nullable = false)
    @Setter
    @NotNull
    private Long writerId;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    @Setter
    @NotNull
    private Task task;

    @Builder
    public Comment(String content, Long writerId, Task task) {
        this.content = content;
        this.writerId = writerId;
        this.task = task;
    }
}
