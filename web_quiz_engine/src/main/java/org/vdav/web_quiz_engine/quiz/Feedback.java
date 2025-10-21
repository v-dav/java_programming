package org.vdav.web_quiz_engine.quiz;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Feedback {

    SUCCESS("Congratulations, you're right!"),
    FAILURE("Wrong answer! Please, try again.");

    private final String message;

    Feedback(String message) {
        this.message = message;
    }

    @JsonValue
    public String getMessage() {
        return message;
    }
}
