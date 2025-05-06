package com.geulowup.backend.domain.template.repository;

import com.geulowup.backend.domain.template.entity.UserTemplate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTemplateRepository extends JpaRepository<UserTemplate, Long> {

    Optional<UserTemplate> findByFolderUserIdAndTemplateId(Long userId, Long templateId);
}