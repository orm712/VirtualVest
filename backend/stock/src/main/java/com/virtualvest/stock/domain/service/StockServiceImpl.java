package com.virtualvest.stock.domain.service;

import com.virtualvest.stock.domain.dto.response.StockResDTO;
import com.virtualvest.stock.domain.entity.Stock;
import com.virtualvest.stock.domain.repository.StockRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {
    private final StockRepository stockRepository;

    @Transactional(readOnly = true)
    public StockResDTO getStockById(long seq){
        Stock stock = stockRepository.findById(seq).orElse(null);

        return StockResDTO.builder()
                .seq(seq)
                .name(stock.getName())
                .currentPrice(stock.getCurrentPrice())
                .build();
    }
}
