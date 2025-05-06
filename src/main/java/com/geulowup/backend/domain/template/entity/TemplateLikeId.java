package com.geulowup.backend.domain.template.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class TemplateLikeId {
    @Column(name = "template_id")
    private Long templateId;

    @Column(name = "user_id")
    private Long userId;
}
