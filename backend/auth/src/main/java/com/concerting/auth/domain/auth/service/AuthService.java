package com.concerting.auth.domain.auth.service;

import com.concerting.auth.domain.auth.dto.request.SignInReqDTO;
import com.concerting.auth.domain.auth.dto.request.SignUpReqDTO;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    public void signup(SignUpReqDTO signUpReqDTO);

    public ResponseEntity<?> signIn(SignInReqDTO signInReqDTO);
}
