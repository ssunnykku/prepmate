package com.prepmate.backend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO {
    private Long questionId;
    private String question;
    private String answer;
    private LocalDateTime createdAt;
}
