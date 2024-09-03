package com.prepmate.backend.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterviewReqDTO {
    @Size(max = 200)
    @NotBlank
    private String interviewName;

    @Size(max = 600)
    @NotBlank
    private String description;

    @NotNull(message = "userId must not be null")
    private UUID userId;

}
