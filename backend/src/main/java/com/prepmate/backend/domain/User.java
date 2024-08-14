package com.prepmate.backend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "userId", nullable = false)
    private UUID userId;

    @Column(name = "name", nullable = false)
    @Size(max = 50)
    @NotBlank
    private String name;

    @Column(name = "email", nullable = false)
    @Size(max = 50)
    @NotBlank
    @Email
    private String email;

    @Column(name = "password", nullable = false)
    @Size(max = 50)
    @NotBlank
    private String password;

    @CreatedDate
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user")
    @Builder.Default
    List<Interview> interviews = new ArrayList<>();
}
