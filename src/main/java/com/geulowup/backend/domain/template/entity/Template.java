package com.geulowup.backend.domain.template.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.geulowup.backend.domain.user.entity.User;

@Entity
@Table(name = "templates")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Template {
    @Id
    @Column(name = "template_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User author;

    @Column(nullable = false)
    private String title;

    private String content;

    @Builder.Default
    @Column(name = "like_count", nullable = false)
    private Integer likeCount = 0;

    private String tags;

    @Column(name = "is_private", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean isPrivate = false;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public void updateTemplate(String title, String content, List<String> tags, boolean isPrivate) {
        this.title = title;
        this.content = content;
        this.tags = String.join(",", tags);
        this.isPrivate = isPrivate;
    }

    public boolean isAuthor(Long userId) {
        return author.getId().equals(userId);
    }

    public void addLike() {
        likeCount++;
    }

    public void removeLike() {
        likeCount--;

        if (likeCount <= 0) {
            likeCount = 0;
        }
    }
}
