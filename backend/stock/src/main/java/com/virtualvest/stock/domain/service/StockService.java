package com.virtualvest.stock.domain.service;

import com.virtualvest.stock.domain.dto.response.StockResDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


public interface StockService {
    public StockResDTO getStockById(long seq);
}
