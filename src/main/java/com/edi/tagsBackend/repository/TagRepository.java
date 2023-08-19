package com.edi.tagsBackend.repository;

import com.edi.tagsBackend.models.Tag;
import com.edi.tagsBackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
