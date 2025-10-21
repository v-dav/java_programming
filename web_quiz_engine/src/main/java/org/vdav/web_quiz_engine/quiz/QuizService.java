package org.vdav.web_quiz_engine.quiz;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.vdav.web_quiz_engine.completion.CompletionDTO;
import org.vdav.web_quiz_engine.completion.CompletionEntity;
import org.vdav.web_quiz_engine.completion.CompletionRepository;
import org.vdav.web_quiz_engine.exceptions.QuizDoesNotBelongToUserException;
import org.vdav.web_quiz_engine.exceptions.QuizNotFoundException;
import org.vdav.web_quiz_engine.user.UserEntity;
import org.vdav.web_quiz_engine.user.UserRepository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final CompletionRepository completionRepository;
    private final QuizMapper mapper;

    public QuizService(QuizRepository quizRepository,
                       UserRepository userRepository,
                       CompletionRepository completionRepository,
                       QuizMapper mapper) {
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
        this.completionRepository = completionRepository;
        this.mapper = mapper;
    }

    public QuizDTO addQuiz(CreateQuiz quiz, String username) {
        UserEntity user = getUserEntity(username);

        QuizEntity quizEntity = new QuizEntity(
                quiz.title(),
                quiz.text(),
                quiz.options(),
                quiz.answer()
        );

        user.addQuiz(quizEntity);
        userRepository.save(user);

        return mapper.toDTO(quizEntity);
    }


    public QuizDTO getQuizById(Integer id) {
        Optional<QuizEntity> entity = quizRepository.findById(id);
        if (entity.isPresent()) {
            return mapper.toDTO(entity.get());
        }
        throw new QuizNotFoundException(id);
    }

    public Page<QuizDTO> getAllQuizzesPageable(Integer page) {
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        Page<QuizEntity> quizPage = quizRepository.findAll(pageable);
        return quizPage.map(mapper::toDTO);
    }

    public QuizResponse answerQuiz(Integer id, Answer answer, String username) {
        Optional<QuizEntity> quizEntityOptional = quizRepository.findById(id);
        if (quizEntityOptional.isPresent()) {
            UserEntity user = getUserEntity(username);
            QuizEntity quizEntity = quizEntityOptional.get();

            Set<Integer> entityAnswers = quizEntity.getAnswers();
            HashSet<Integer> correctAnswers = entityAnswers != null ? new HashSet<>(entityAnswers) : new HashSet<>();
            Set<Integer> userAnswers = answer.answer() != null ? answer.answer() : new HashSet<>();

            if (correctAnswers.equals(userAnswers)) {
                user.addQuizCompletion(new CompletionEntity(quizEntity.getId(), LocalDateTime.now()));
                userRepository.save(user);
                return new QuizResponse(true, Feedback.SUCCESS);
            } else {
                return new QuizResponse(false, Feedback.FAILURE);
            }
        }
        throw new QuizNotFoundException(id);
    }

    public void deleteQuiz(Integer id, String username) {
        Optional<QuizEntity> quizEntity = quizRepository.findById(id);
        if (quizEntity.isPresent()) {
            UserEntity user = getUserEntity(username);
            if (!user.getQuizzes().contains(quizEntity.get())) {
                throw new QuizDoesNotBelongToUserException("Quiz does not belong to user");
            }
            user.getQuizzes().remove(quizEntity.get());
            userRepository.save(user);
            return;
        }
        throw new QuizNotFoundException(id);
    }

    private UserEntity getUserEntity(String username) {
        UserEntity user = userRepository.findUserEntityByUsername(username);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("Not found");
        }
        return user;
    }

    public Page<CompletionDTO> getUserCompletions(String username, Integer page) {
        int size = 10;
        UserEntity user = getUserEntity(username);
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "completedAt"));
        Page<CompletionEntity> completionEntityPage = completionRepository.findByUser(user, pageable);
        return completionEntityPage.map(mapper::toCompletionDTO);
    }
}
