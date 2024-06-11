package com.virtualvest.payment.domain.service;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.response.IamportResponse;

import com.virtualvest.payment.domain.dto.request.PayReqDTO;
import com.virtualvest.payment.domain.entity.Payment;
import com.virtualvest.payment.domain.repository.PaymentRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Value("${iamport.api_key}")
    private String apiKey;

    @Value("${iamport.api_secret}")
    private String apiSecret;

    private IamportClient iamportClient;

    @PostConstruct
    public void init() {
        this.iamportClient = new IamportClient(apiKey, apiSecret);
    }

    @Transactional
    public void chargeBalance(PayReqDTO payReqDTO){

        IamportResponse<com.siot.IamportRestClient.response.Payment> paymentResponse = iamportClient.paymentByImpUid(payReqDTO.getImp_uid());

        if (paymentResponse.getResponse() != null && paymentResponse.getResponse().getStatus().equals("paid")) {
            Payment payment = paymentRepository.findByUserSeq(payReqDTO.getUserSeq())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid user sequence"));

            payment.setBalance(payment.getBalance() + payment.getBalance());
            paymentRepository.save(payment);
        } else {
            throw new IllegalArgumentException("Payment verification failed");
        }
    }

    //유저 새로 생성하면 계좌 생성
    @Transactional
    public void createAccount(long userSeq){
        Payment payment = Payment.builder()
                .userSeq(userSeq)
                .stockSeq(0)
                .balance(0)
                .build();

        try{
            paymentRepository.save(payment);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
