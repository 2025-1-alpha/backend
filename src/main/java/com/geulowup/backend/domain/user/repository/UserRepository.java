package com.geulowup.backend.domain.user.repository;

import com.geulowup.backend.domain.user.entity.User;
import com.geulowup.backend.domain.user.enums.SocialType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findBySocialIdAndSocialType(String socialId, SocialType socialType);
    Optional<User> findByEmail(String email);
}
