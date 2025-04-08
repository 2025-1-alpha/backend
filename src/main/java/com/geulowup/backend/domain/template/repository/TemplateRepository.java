package com.geulowup.backend.domain.template.repository;

import com.geulowup.backend.domain.template.entity.Template;
import com.geulowup.backend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TemplateRepository extends JpaRepository<Template, Long> {



}
