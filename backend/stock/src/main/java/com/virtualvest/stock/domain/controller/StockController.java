package com.virtualvest.stock.domain.controller;

import com.virtualvest.stock.domain.dto.response.StockResDTO;
import com.virtualvest.stock.domain.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stock")
public class StockController {
    private final StockService stockService;

    @GetMapping("/{seq}/price")
    public ResponseEntity<?> getStockPrice(@PathVariable("seq") long seq) {
        StockResDTO stock = stockService.getStockById(seq);
        return ResponseEntity.ok(stock);
    }
}
