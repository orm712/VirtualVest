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

    @Value("${iamport.api_key}")
    private String apiKey;

    @Value("${iamport.api_secret}")
    private String apiSecret;

    private IamportClient iamportClient;

    @PostConstruct
    public void init() {
        this.iamportClient = new IamportClient(apiKey, apiSecret);
    }

    @PostMapping("/order/payment")
    public ResponseEntity<String> paymentComplete(@RequestBody PayReqDTO payReqDTO) throws IOException {
//        try {
//
//
//
////            iamportClient.paymentByImpUid()
//            paymentService.saveOrder(payReqDTO);
//            return ResponseEntity.ok().build();
//        } catch (RuntimeException e) {
////            String token = refundService.getToken(apiKey, apiSecret);
////            refundService.refundWithToken(token, orderNumber, e.getMessage());
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }

//        String merchantUid = "merchant_" + System.currentTimeMillis();  // Unique merchant ID
//
//
//
//        OnetimePaymentData onetimePayment = new OnetimePaymentData(merchantUid, new BigDecimal(payReqDTO.getBalance()),);
//        IamportResponse<Payment> response = iamportClient.onetimePayment(onetimePayment);
//
//        Payment payment = response.getResponse();


        return ResponseEntity.ok().build();
    }

    @PostMapping("/verifyIamport/{imp_uid}")
    public IamportResponse<Payment> paymentByImpUid(@PathVariable("imp_uid") String imp_uid) throws Exception {
        return iamportClient.paymentByImpUid(imp_uid);
    }


    @PostMapping("/payment/validation/{imp_uid}")
    public IamportResponse<Payment> validateIamport(@PathVariable String imp_uid) {
        IamportResponse<Payment> payment = iamportClient.paymentByImpUid(imp_uid);
        log.info("결제 요청 응답. 결제 내역 - 주문 번호: {}", payment.getResponse().getMerchantUid());
        return payment;
    }
}
