package com.virtualvest.payment.domain.service;

import com.virtualvest.payment.domain.dto.request.PayReqDTO;

public interface PaymentService {
    public void saveOrder(PayReqDTO payReqDTO);
}
