package com.prepmate.backend.repository;

import com.prepmate.backend.domain.Interview;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;


public interface InterviewRepository extends JpaRepository<Interview, Long> {
    Page<Interview> findAll(Pageable pageable);
}
