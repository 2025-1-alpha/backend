package com.geulowup.backend.domain.template.repository;

import com.geulowup.backend.domain.template.entity.Template;
import com.geulowup.backend.domain.template.entity.TemplateLike;
import com.geulowup.backend.domain.template.entity.TemplateLikeId;
import com.geulowup.backend.domain.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemplateLikeRepository extends JpaRepository<TemplateLike, TemplateLikeId> {
    boolean existsByTemplateAndUser(Template template, User user);

    void deleteByTemplateAndUser(Template template, User user);

    List<TemplateLike> findAllByUserId(Long userId);
}
