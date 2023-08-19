package com.edi.tagsBackend.repository;

import com.edi.tagsBackend.models.Link;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<Link, Long> {
    Link findByValue(String value);
}
