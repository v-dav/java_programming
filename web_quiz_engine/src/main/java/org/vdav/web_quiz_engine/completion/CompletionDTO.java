package org.vdav.web_quiz_engine.completion;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record CompletionDTO(Integer id,
                            @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
                            LocalDateTime completedAt) {
}
