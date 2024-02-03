package com.concerting.auth.global.security.service;

import com.concerting.auth.domain.auth.entity.User;
import com.concerting.auth.domain.auth.enums.Role;
import com.concerting.auth.domain.auth.exception.UserNotFoundException;
import com.concerting.auth.domain.auth.repository.AuthRepository;
import com.concerting.auth.global.security.entity.Token;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Collections;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtProvider {
    @Value("${jwt.secret.key}")
    private String salt;

    @Value("${jwt.access_token.expiration_time}")
    private long ACCESS_TOKEN_EXPIRATION_TIME;

    @Value("${jwt.refresh_token.expiration_time}")
    private long REFRESH_TOKEN_EXPIRATION_TIME;

    private Key SECRET_KEY;

    private final AuthRepository authRepository;

    @PostConstruct
    protected void init() {
        SECRET_KEY = Keys.hmacShaKeyFor(salt.getBytes());
    }

    public Token generateToken(String email, Role role){
        String accessToken = Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME * 1000))
                .signWith(SECRET_KEY)
                .compact();

        String refreshToken = Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME * 1000))
                .signWith(SECRET_KEY)
                .compact();

        return new Token(accessToken, refreshToken);
    }

    public Authentication getAuthentication(String email){
        User user = authRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("존재하지 않는 아이디입니다."));
        return new UsernamePasswordAuthenticationToken(user, null, Collections.singleton(new SimpleGrantedAuthority(user.getRole().name())));
    }

//    public boolean validToken(String token){
//        try{
//            Claims claims = getClaims(token);
//
//        }
//    }

    //토큰에서 claims 추출
    private Claims getClaims(String token){
        Claims claims;
        try{
            claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e){
            log.info("만료된 토큰");
            throw new BadCredentialsException("만료된 토큰입니다.", e);
        } catch (MalformedJwtException e) {
            log.info("유효하지 않은 구성의 토큰");
            throw new BadCredentialsException("유효하지 않은 구성의 토큰", e);
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 형식이나 구성의 토큰");
            throw new BadCredentialsException("지원되지 않는 형식이나 구성의 토큰", e);
        } catch (IllegalArgumentException e) {
            log.info("잘못된 입력값");
            throw new BadCredentialsException("잘못된 입력값", e);
        }
        return claims;
    }

    private void logGenerateToken(String email, Role role) {
        log.info("=====" + "\t" +
                "JWT 토큰 생성" + "\t" +
                "email: " + email + "\t" +
                "role: " + role + "\t" +
                "=====");
    }
}
