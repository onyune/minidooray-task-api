package com.nhnacademy.minidoorayteam9taskapi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.nhnacademy.minidoorayteam9taskapi.entity.Project;
import com.nhnacademy.minidoorayteam9taskapi.entity.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class TagRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TagRepository tagRepository;

    @Test
    void saveAndFindTest() {
        Project project = Project.builder().name("P").build();
        entityManager.persist(project);
        
        Tag tag = Tag.builder().name("Tag1").project(project).build();
        tagRepository.save(tag);
        entityManager.flush();

        Tag found = tagRepository.findById(tag.getId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Tag1");
    }
}
