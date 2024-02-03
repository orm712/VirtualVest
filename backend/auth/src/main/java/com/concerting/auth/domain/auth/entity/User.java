package com.concerting.auth.domain.auth.entity;

import com.concerting.auth.domain.auth.enums.Role;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public User(String email, String password, String phone, String nickname, LocalDate birthday, Role role) {
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.nickname = nickname;
        this.birthday = birthday;
        this.role = role;
    }
}
