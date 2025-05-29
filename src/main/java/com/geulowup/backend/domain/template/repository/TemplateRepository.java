package com.geulowup.backend.domain.template.repository;

import com.geulowup.backend.domain.template.entity.Template;
import com.geulowup.backend.domain.user.entity.User;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TemplateRepository extends JpaRepository<Template, Long> {
    List<Template> findTop5ByIsPrivateIsFalseOrderByLikeCountDesc();

    List<Template> findAllByIsPrivateIsFalseOrderByLikeCountDesc();

    List<Template> findAllByAuthorAndIsPrivateIsFalseOrderByCreatedAtDesc(User author);

    List<Template> findAllByAuthorOrderByCreatedAtDesc(User author);

    @Query("""
    SELECT t FROM Template t
    WHERE (:search IS NULL OR t.title LIKE CONCAT('%', :search, '%'))
    AND (t.isPrivate IS FALSE)
    AND (:tag IS NULL OR t.tags LIKE CONCAT('%', :tag, '%'))
    """)
    Page<Template> findAllByFiltering(@Param("search") String search, @Param("tag") String tag, Pageable pageable);
}
