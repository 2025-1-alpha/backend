package com.geulowup.backend.domain.template.repository;

import com.geulowup.backend.domain.template.entity.UserTemplateFolder;
import com.geulowup.backend.domain.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFolderRepository extends JpaRepository<UserTemplateFolder, Long> {
    List<UserTemplateFolder> findAllByUser(User user);

    boolean existsByUserAndName(User user, String name);
}
