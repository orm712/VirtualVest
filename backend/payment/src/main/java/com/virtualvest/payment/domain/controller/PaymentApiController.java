package com.virtualvest.payment.domain.controller;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.request.CardInfo;
import com.siot.IamportRestClient.request.OnetimePaymentData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import com.virtualvest.payment.domain.dto.request.PayReqDTO;
import com.virtualvest.payment.domain.service.PaymentService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;


@Slf4j
@RestController
@RequiredArgsConstructor
//@RequestMapping("/api/v1/payment")
public class PaymentApiController {

    private final PaymentService paymentService;

    @PostMapping("/verifyIamport/{imp_uid}")
    public ResponseEntity<?> paymentByImpUid(@PathVariable("imp_uid") String imp_uid, @RequestBody PayReqDTO payReqDTO) throws Exception {
        payReqDTO.setImp_uid(imp_uid);
        log.info("충전 시작");
        try {
            paymentService.chargeBalance(payReqDTO);
            return ResponseEntity.ok("충전 성공");
        } catch (Exception e) {
            log.error("충전 실패", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("충전 실패");
        }
    }

    //계정 생성 후 계좌 생성
    @PostMapping("/createAccount")
    public ResponseEntity<?> createAccount(@RequestParam long userSeq){
        paymentService.createAccount(userSeq);
        return ResponseEntity.ok("계좌 생성 성공");
    }

}
