package com.v02.concurrency.stock.service.sync;

import com.v02.concurrency.response.RestResult;
import com.v02.concurrency.stock.controller.ReqStockSetParam;
import com.v02.concurrency.stock.controller.ReqStockUseOnceParam;
import com.v02.concurrency.stock.repository.entity.StockEntity;
import com.v02.concurrency.stock.repository.sync.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockPessimisticService implements StockServiceInterface {

    private final StockRepository stockRepository;

    @Override
    @Transactional
    public RestResult setStockQuantity(ReqStockSetParam param) {
        StockEntity entity = new StockEntity(param.getName(),param.getQuantity());
        entity = stockRepository.save(entity);

        return new RestResult("success","save success", Map.of("stockResult",entity));
    }

    @Override
    @Transactional
    public RestResult useStockOnce(ReqStockUseOnceParam param){
        StockEntity findResult = stockRepository.findByNameWithPessimisticLock(param.getName());

        if(findResult.getQuantity() <= 0){
            throw new RuntimeException("재고가 부족합니다.");
        }

        findResult.setQuantity(findResult.getQuantity()-1);
        StockEntity resultEntity = stockRepository.save(findResult);
        return new RestResult("success","use stock success", Map.of("stockUseResult",resultEntity));
    }
}
