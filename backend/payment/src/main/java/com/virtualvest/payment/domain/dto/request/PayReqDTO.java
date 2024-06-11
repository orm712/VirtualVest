package com.virtualvest.payment.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayReqDTO {
    private String imp_uid;
    private long userSeq;
    private int balance;
}
