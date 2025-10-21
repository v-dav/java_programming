package org.vdav.web_quiz_engine.quiz;

import java.util.List;

public record QuizDTO(Integer id, String title, String text, List<String> options) {
}
