package org.vdav.web_quiz_engine.quiz;

import org.springframework.stereotype.Service;
import org.vdav.web_quiz_engine.completion.CompletionDTO;
import org.vdav.web_quiz_engine.completion.CompletionEntity;

import java.util.List;

@Service
public class QuizMapper {

    public QuizDTO toDTO(QuizEntity entity) {
        return new QuizDTO(entity.getId(), entity.getTitle(), entity.getText(), entity.getOptions());
    }

    public List<CompletionDTO> allCompletionsToDTO(List<CompletionEntity> completionEntities) {
        return completionEntities.stream().map(this::toCompletionDTO).toList();
    }

    public CompletionDTO toCompletionDTO(CompletionEntity completionEntity) {
        return new CompletionDTO(completionEntity.getQuizId(), completionEntity.getCompletedAt());
    }
}
