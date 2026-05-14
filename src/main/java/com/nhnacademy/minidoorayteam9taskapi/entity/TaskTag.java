package com.nhnacademy.minidoorayteam9taskapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "task_tags")
@Getter
public class TaskTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_tag_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id")
    @Setter
    @NotNull
    private Task task;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    @Setter
    @NotNull
    private Tag tag;

    @Builder
    public TaskTag(Task task, Tag tag) {
        this.task = task;
        this.tag = tag;
    }
}
