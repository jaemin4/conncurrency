package com.v02.concurrency.stock;

import com.v02.concurrency.RestResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockEntityRepository stockEntityRepository;
    @Transactional
    public RestResult setStockQuantity(ReqStockSetParam param){
        StockEntity entity = new StockEntity(param.getName(),param.getQuantity());
        entity = stockEntityRepository.save(entity);

        return new RestResult("success","save success", Map.of("stockResult",entity));
    }

    @Transactional
    public RestResult useStockOnce(ReqStockUseOnceParam param){
        StockEntity findResult = stockEntityRepository.findByNameWithPessimisticLock(param.getName());

        if(findResult.getQuantity() <= 0){
            throw new RuntimeException("재고가 부족합니다.");
        }

        findResult.setQuantity(findResult.getQuantity()-1);
        StockEntity resultEntity = stockEntityRepository.save(findResult);
        return new RestResult("success","use stock success", Map.of("stockUseResult",resultEntity));
    }






}
