package com.concerting.auth.domain.auth.service;

import com.concerting.auth.domain.auth.dto.request.SignUpReqDTO;

public interface AuthService {
    public void signup(SignUpReqDTO signUpReqDTO);
}
