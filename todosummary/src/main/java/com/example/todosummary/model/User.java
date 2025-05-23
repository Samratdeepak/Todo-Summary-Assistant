package com.example.todosummary.model;

import lombok.*;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true)
    private String username;

    private String password;
    private Boolean enabled = false;
    private String otp;
    private LocalDateTime otpExpiry;
}