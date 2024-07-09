package com.virtualvest.stock.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long seq;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int currentPrice;

    //빌더
    @Builder
    public Stock(String name, int currentPrice) {
        this.name = name;
        this.currentPrice = currentPrice;
    }
}
