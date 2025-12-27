package com.agamera.school_app.repository;

import com.agamera.school_app.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<School, Long> {
}