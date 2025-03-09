package com.v02.concurrency.stock.controller;

import com.v02.concurrency.response.RestResult;
import com.v02.concurrency.stock.service.asyn.StockRabbitService;
import com.v02.concurrency.stock.service.sync.StockMainService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StockController {

    private final StockMainService stockMainService;
    private final StockRabbitService stockRabbitService;

    @PostMapping("/set")
    public RestResult setStock(@RequestBody ReqStockSetParam param){
        return stockMainService.setStockQuantity(param);
    }

    @PostMapping("/use")
    public RestResult useStock(@RequestBody ReqStockUseOnceParam param){
        return stockMainService.useStockOnce(param);
    }

    @PostMapping("/rabbit/use")
    public RestResult useStockRabbit(@RequestBody ReqStockUseOnceParam param){
            stockRabbitService.useStockOnce(param);


        return new RestResult("??","비동기 처리");
    }

}
