package com.geulowup.backend.domain.template.repository;

import com.geulowup.backend.domain.template.entity.UserTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTemplateRepository extends JpaRepository<UserTemplate, Long> {

    boolean existsByFolderIdAndTemplateId(Long folderId, Long templateId);
}