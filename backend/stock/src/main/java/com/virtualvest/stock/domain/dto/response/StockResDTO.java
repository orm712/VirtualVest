package com.virtualvest.stock.domain.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class StockResDTO {
    private long seq;
    private String name;
    private int currentPrice;

    @Builder
    public StockResDTO(long seq, String name, int currentPrice) {
        this.seq = seq;
        this.name = name;
        this.currentPrice = currentPrice;
    }
}
