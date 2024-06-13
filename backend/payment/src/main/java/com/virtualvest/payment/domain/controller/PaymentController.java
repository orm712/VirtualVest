package com.virtualvest.payment.domain.controller;

import com.virtualvest.payment.domain.dto.request.PayReqDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class PaymentController {
    @GetMapping("/api/v1/payment/pay")
    public String paymentPage() {
        return "payment";
    }

}
