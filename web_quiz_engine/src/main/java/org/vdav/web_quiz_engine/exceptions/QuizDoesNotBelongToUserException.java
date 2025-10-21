package org.vdav.web_quiz_engine.exceptions;

public class QuizDoesNotBelongToUserException extends RuntimeException {
    public QuizDoesNotBelongToUserException(String message) {
        super(message);
    }
}
