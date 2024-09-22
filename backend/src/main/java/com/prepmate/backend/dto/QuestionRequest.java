package com.prepmate.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionRequest {
    @Size(max = 600)
    @NotBlank
    private String question;

    @Size(max = 3000)
    @NotBlank
    private String answer;

    @NotNull(message = "interviewId must not be null")
    private Long interviewId;

}
