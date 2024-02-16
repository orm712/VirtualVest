package com.concerting.auth.domain.auth.service;

import com.concerting.auth.domain.auth.dto.request.SignInReqDTO;
import com.concerting.auth.domain.auth.dto.request.SignUpReqDTO;
import com.concerting.auth.domain.auth.dto.response.SignInResDTO;
import com.concerting.auth.domain.auth.entity.User;
import com.concerting.auth.domain.auth.exception.DatabaseException;
import com.concerting.auth.domain.auth.exception.PasswordNotMatchException;
import com.concerting.auth.domain.auth.exception.UserNotFoundException;
import com.concerting.auth.domain.auth.repository.AuthRepository;
import com.concerting.auth.global.redis.repository.RefreshTokenRedisRepository;
import com.concerting.auth.global.security.entity.RefreshToken;
import com.concerting.auth.global.security.entity.Token;
import com.concerting.auth.global.security.service.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RefreshScope
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    private final RefreshTokenRedisRepository refreshTokenRedisRepository;

    public void signup(SignUpReqDTO signUpReqDTO){
        log.info("signup");
        User user = User.builder()
                .email(signUpReqDTO.email())
                .password(passwordEncoder.encode(signUpReqDTO.password()))
                .nickname(signUpReqDTO.nickname())
                .birthday(signUpReqDTO.birthday())
                .phone(signUpReqDTO.phone())
                .build();
        try{
            authRepository.save(user);
        } catch(Exception e){
            throw new DatabaseException("DB 접근 오류");
        }
    }

    public ResponseEntity<?> signIn(SignInReqDTO signInReqDTO){
        log.info("signin");

        User user = authRepository.findByEmail(signInReqDTO.email())
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자"));

        if(!passwordEncoder.matches(signInReqDTO.password(), user.getPassword())){
            throw new PasswordNotMatchException("비밀번호가 일치하지 않습니다.");
        }

        Token token = jwtProvider.generateToken(user.getEmail(), user.getRole());

        try{
            refreshTokenRedisRepository.save(RefreshToken.builder()
                    .email(user.getEmail())
                    .refreshToken(token.refreshToken())
                    .build());
        } catch (Exception e){
            throw new DatabaseException("DB 접근 오류");
        }

        SignInResDTO signInResDTO = new SignInResDTO(token.accessToken(), token.refreshToken());

        return ResponseEntity.ok().body(signInResDTO);
    }
}
