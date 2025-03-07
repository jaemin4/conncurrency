package com.v02.concurrency.stock;

import com.v02.concurrency.RestResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockEntityRepository stockEntityRepository;
    public RestResult setStockQuantity(ReqStockSetParam param){
        StockEntity entity = new StockEntity(param.getQuantity());
        entity = stockEntityRepository.save(entity);

        return new RestResult("success","save success", Map.of("stockResult",entity));
    }





}
