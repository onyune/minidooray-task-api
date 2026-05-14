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
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tasks")
@Getter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id", nullable = false)
    private Long id;

    @Column(name = "task_name", nullable = false, length = 100)
    @Setter
    @NotBlank
    @Size(min = 1, max = 100)
    private String name;

    @Column(name = "content", nullable = false, columnDefinition = "LONGTEXT")
    @Setter
    @NotBlank
    private String content;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    @Setter
    @NotNull
    private Project project;

    @ManyToOne
    @JoinColumn(name = "milestone_id")
    @Setter
    private Milestone milestone;

    @Builder
    public Task(String name, String content, Project project, Milestone milestone) {
        this.name = name;
        this.content = content;
        this.project = project;
        this.milestone = milestone;
    }
}
