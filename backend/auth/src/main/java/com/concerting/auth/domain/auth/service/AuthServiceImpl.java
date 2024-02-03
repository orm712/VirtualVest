package com.concerting.auth.domain.auth.service;

import com.concerting.auth.domain.auth.dto.request.SignUpReqDTO;
import com.concerting.auth.domain.auth.entity.User;
import com.concerting.auth.domain.auth.exception.DatabaseException;
import com.concerting.auth.domain.auth.repository.AuthRepository;
import com.concerting.auth.global.security.service.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

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
}
