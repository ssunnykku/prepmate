package com.prepmate.backend.repository;

import com.prepmate.backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(UUID userId);
}
