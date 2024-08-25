package com.concerting.auth.global.security.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor
public class User {
    // seq는 PK, AI(따라서 IDENTITY로 키 생성을 DB에 위임)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;
    // email과 profile은 null 가능이므로 별도의 not null 설정해주지 않음(디폴트 값이 nullable = true)
    // 또한, 문자열 길이 역시 기본값인 255와 실제 DB의 길이인 255가 동일하므로 제약 명시 X
    private String email;

    @Column(nullable = false)
    private String ROLE;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private Date birthday;

    private String profile;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OauthProvider oauth_provider_id;

    @Column(nullable = false)
    private String oauth_id;

    @Builder
    public User(int seq, String email, String ROLE, String phone, Date birthday, String profile, String nickname, OauthProvider oauth_provider_id, String oauth_id) {
        this.seq = seq;
        this.email = email;
        this.ROLE = ROLE;
        this.phone = phone;
        this.birthday = birthday;
        this.profile = profile;
        this.nickname = nickname;
        this.oauth_provider_id = oauth_provider_id;
        this.oauth_id = oauth_id;
    }
}
