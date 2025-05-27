package com.geulowup.backend.domain.template.repository;

import com.geulowup.backend.domain.template.entity.UserTemplate;
import com.geulowup.backend.domain.template.entity.UserTemplateHistory;
import com.geulowup.backend.domain.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserHistoryRepository extends JpaRepository<UserTemplateHistory, Long> {
    List<UserTemplateHistory> findAllByUserOrderByUsedAtDesc(User user);
}

