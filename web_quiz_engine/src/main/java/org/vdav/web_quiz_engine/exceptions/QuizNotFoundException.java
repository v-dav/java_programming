package org.vdav.web_quiz_engine.exceptions;

public class QuizNotFoundException extends RuntimeException{
    public QuizNotFoundException(String message) {
        super(message);
    }

    public QuizNotFoundException(Integer id) {
        super("Quiz with id " + id + " not found");
    }
}
