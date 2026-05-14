package com.nhnacademy.minidoorayteam9taskapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "milestones")
@Getter
public class Milestone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "milestone_id", nullable = false)
    private Long id;

    @Column(name = "milestone_name", nullable = false, length = 100)
    @Setter
    private String name;

    @Column(name = "start_at", columnDefinition = "DATE")
    @Setter
    private LocalDate startAt;

    @Column(name = "end_at", columnDefinition = "DATE")
    @Setter
    private LocalDate endAt;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @Setter
    private Project project;

    @Builder
    public Milestone(String name, LocalDate startAt, LocalDate endAt, Project project) {
        this.name = name;
        this.startAt = startAt;
        this.endAt = endAt;
        this.project = project;
    }

}
