package com.v02.concurrency.stock;

import com.v02.concurrency.RestResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @PostMapping("/set")
    public RestResult setStock(@RequestBody ReqStockSetParam param){
        return stockService.setStockQuantity(param);
    }

    @PostMapping("/use")
    public RestResult useStock(@RequestBody ReqStockUseOnceParam param){
        return stockService.useStockOnce(param);
    }

}
