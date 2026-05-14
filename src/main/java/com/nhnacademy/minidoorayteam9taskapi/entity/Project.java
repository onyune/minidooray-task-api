package com.nhnacademy.minidoorayteam9taskapi.entity;

import com.nhnacademy.minidoorayteam9taskapi.entity.enums.ProjectStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "projects")
@Getter
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id", nullable = false)
    private Long id;

    @Column(name = "project_name", nullable = false)
    @Setter
    @NotBlank
    @Size(min = 1, max = 255)
    private String name;

    @Column(name = "status",nullable = false)
    @Setter
    @Enumerated(EnumType.STRING)
    @NotNull
    private ProjectStatus status;

    @Builder
    public Project(String name) {
        this.name = name;
        this.status = ProjectStatus.ACTIVE;
    }
}
