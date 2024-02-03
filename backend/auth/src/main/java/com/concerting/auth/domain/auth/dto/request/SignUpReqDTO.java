package com.concerting.auth.domain.auth.dto.request;

import java.time.LocalDate;

public record SignUpReqDTO(String email, String password, String nickname, LocalDate birthday, String phone) {
}
