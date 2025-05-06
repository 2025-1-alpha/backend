package com.geulowup.backend.domain.template.repository;

import com.geulowup.backend.domain.template.entity.UserTemplateHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserHistoryRepository extends JpaRepository<UserTemplateHistory, Long> {}

