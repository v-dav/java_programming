package org.vdav.web_quiz_engine.quiz;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.vdav.web_quiz_engine.completion.CompletionDTO;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    private final QuizService service;

    public QuizController(QuizService service) {
        this.service = service;
    }

    @PostMapping()
    public ResponseEntity<QuizDTO> addQuiz(@Valid @RequestBody CreateQuiz quiz, Authentication authentication) {
        return new ResponseEntity<>(service.addQuiz(quiz, authentication.getName()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizDTO> getQuizById(@PathVariable Integer id) {
        return new ResponseEntity<>(service.getQuizById(id), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<Page<QuizDTO>> getAllQuizzes(@RequestParam Integer page) {
        return new ResponseEntity<>(service.getAllQuizzesPageable(page), HttpStatus.OK);
    }

    @PostMapping("/{id}/solve")
    public ResponseEntity<QuizResponse> answerQuizById(@PathVariable Integer id,
                                                       @RequestBody Answer answer,
                                                       Authentication authentication) {
        return new ResponseEntity<>(service.answerQuiz(id, answer, authentication.getName()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteQuiz(@PathVariable Integer id, Authentication authentication) {
        service.deleteQuiz(id, authentication.getName());
    }

    @GetMapping("/completed")
    public ResponseEntity<Page<CompletionDTO>> getUserCompletions(@RequestParam Integer page,
                                                                  Authentication authentication) {
        return new ResponseEntity<>(service.getUserCompletions(authentication.getName(), page), HttpStatus.OK);
    }
}
