package org.vdav.web_quiz_engine.completion;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.vdav.web_quiz_engine.BaseEntity;
import org.vdav.web_quiz_engine.user.UserEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "completion")
public class CompletionEntity extends BaseEntity {

    private Integer quizId;
    private LocalDateTime completedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserEntity user;

    public CompletionEntity() {
    }

    public CompletionEntity(Integer quizId, LocalDateTime completedAt) {
        this.quizId = quizId;
        this.completedAt = completedAt;
    }

    public Integer getQuizId() {
        return quizId;
    }

    public void setQuizId(Integer quizId) {
        this.quizId = quizId;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
