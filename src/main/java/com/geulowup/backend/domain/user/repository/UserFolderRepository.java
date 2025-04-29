package com.geulowup.backend.domain.user.repository;

import com.geulowup.backend.domain.user.entity.UserTemplateFolder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFolderRepository extends JpaRepository<UserTemplateFolder, Long> {
}
