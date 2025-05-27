package com.geulowup.backend.domain.template.repository;

import com.geulowup.backend.domain.template.entity.UserTemplate;
import com.geulowup.backend.domain.template.entity.UserTemplateFolder;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTemplateRepository extends JpaRepository<UserTemplate, Long> {

    Optional<UserTemplate> findByFolderUserIdAndTemplateId(Long userId, Long templateId);

    List<UserTemplate> findAllByFolder(UserTemplateFolder folder);

    boolean existsByFolderUserIdAndTemplateId(Long userId, Long templateId);
}