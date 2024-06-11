package com.virtualvest.payment.domain.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PayReqDTO {
    private long userSeq;
    private int balance;
}
