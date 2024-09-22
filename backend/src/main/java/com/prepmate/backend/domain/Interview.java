package com.prepmate.backend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "interviews")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Interview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interview_id", nullable = false)
    private Long id;

    @Column(name = "interview_name", nullable = false)
    @Size(max = 200)
    @NotBlank
    private String interviewName;

    @Column(name = "description", nullable = false)
    @Size(max = 600)
    @NotBlank
    private String description;

    @CreatedDate
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    public void update(String interviewName, String description) {
        this.interviewName = interviewName;
        this.description = description;
    }

}
