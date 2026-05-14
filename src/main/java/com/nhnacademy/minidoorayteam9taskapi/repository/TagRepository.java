package com.nhnacademy.minidoorayteam9taskapi.repository;

import com.nhnacademy.minidoorayteam9taskapi.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
}
