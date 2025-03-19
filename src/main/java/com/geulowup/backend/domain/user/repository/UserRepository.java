package com.geulowup.backend.domain.user.repository;

import com.geulowup.backend.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findBySocialId(String socialId);
    Optional<User> findByEmail(String email);
}
