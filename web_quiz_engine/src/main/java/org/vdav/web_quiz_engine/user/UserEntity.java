package org.vdav.web_quiz_engine.user;

import jakarta.persistence.*;
import org.vdav.web_quiz_engine.BaseEntity;
import org.vdav.web_quiz_engine.completion.CompletionEntity;
import org.vdav.web_quiz_engine.quiz.QuizEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
public class UserEntity extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private List<QuizEntity> quizzes = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private List<CompletionEntity> quizCompletionEntities = new ArrayList<>();

    public UserEntity() {
    }

    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<QuizEntity> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(List<QuizEntity> quizzes) {
        this.quizzes = quizzes;
    }

    public void addQuiz(QuizEntity quizEntity) {
        this.quizzes.add(quizEntity);
        quizEntity.setUser(this);
    }

    public List<CompletionEntity> getQuizCompletions() {
        return quizCompletionEntities;
    }

    public void setQuizCompletions(List<CompletionEntity> quizCompletionEntities) {
        this.quizCompletionEntities = quizCompletionEntities;
    }

    public void addQuizCompletion(CompletionEntity completionEntity) {
        this.quizCompletionEntities.add(completionEntity);
        completionEntity.setUser(this);
    }
}
