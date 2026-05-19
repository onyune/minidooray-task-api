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

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Table(name = "projects_users")
public class ProjectUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_user_id", nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    @Setter
    @NotNull
    private Long userId;

    @Column(name = "is_admin", nullable = false)
    @Setter
    @NotNull
    private boolean isAdmin;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @Setter
    @NotNull
    private Project project;

    @Builder
    public ProjectUser(Long userId, boolean isAdmin, Project project) {
        this.userId = userId;
        this.isAdmin = isAdmin;
        this.project = project;
    }
}
