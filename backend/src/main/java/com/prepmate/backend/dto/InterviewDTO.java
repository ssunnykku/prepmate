package com.prepmate.backend.dto;
import java.time.LocalDateTime;

public class InterviewDTO {
    private Long id;
    private String interviewName;
    private String description;
    private LocalDateTime createdAt;
    private UserDTO user;

}
