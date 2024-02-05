package com.concerting.auth.domain.auth.controller;

import com.concerting.auth.domain.auth.dto.request.SignUpReqDTO;
import com.concerting.auth.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignUpReqDTO signUpReqDTO){
        log.info("signup");
        authService.signup(signUpReqDTO);
        return ResponseEntity.ok("회원가입 성공");
    }
}