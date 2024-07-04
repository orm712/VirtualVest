package com.virtualvest.payment.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column(nullable = false)
    private long userSeq;

    @Column(nullable = false)
    private long stockSeq;

    @Column(nullable = false)
    private int balance;

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Builder
    public Payment(long userSeq, long stockSeq, int balance) {
        this.userSeq = userSeq;
        this.stockSeq = stockSeq;
        this.balance = balance;
    }
}
