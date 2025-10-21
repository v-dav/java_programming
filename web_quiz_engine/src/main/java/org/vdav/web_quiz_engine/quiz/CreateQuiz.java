package org.vdav.web_quiz_engine.quiz;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Set;

public record CreateQuiz(@NotNull(message = "Title should not be null")
                         @NotBlank(message = "Title should not be blank") String title,

                         @NotNull(message = "Text should not be null")
                         @NotBlank(message = "Text should not be blank") String text,

                         @NotNull
                         @Size(min = 2, message = "Options should contain at least 2 elements")
                         List<String> options,

                         Set<Integer> answer) {
}
