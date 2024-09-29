package com.prepmate.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class InterviewDTO {
    private Long interviewId;
    private String interviewName;
    private String description;
    private LocalDateTime createdAt;
    private UUID userId;

}
