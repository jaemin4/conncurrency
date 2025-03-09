package com.v02.concurrency.stock.service.sync;

import com.v02.concurrency.response.RestResult;
import com.v02.concurrency.stock.controller.ReqStockSetParam;
import com.v02.concurrency.stock.controller.ReqStockUseOnceParam;
import com.v02.concurrency.stock.repository.sync.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockOptimisticLockService implements StockServiceInterface {

    private final StockRepository stockRepository;

    @Override
    public RestResult setStockQuantity(ReqStockSetParam reqStockSetParam) {
        return null;
    }

    @Override
    public RestResult useStockOnce(ReqStockUseOnceParam reqStockUseOnceParam) {
        return null;
    }
}
