package com.prepmate.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterviewDTO {
    private Long id;
    private String interviewName;
    private String description;
    private LocalDateTime createdAt;
    private UserDTO user;
    List<QuestionDTO> questions = new ArrayList<>();

}
