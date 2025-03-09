package com.v02.concurrency.stock.service.asyn;

import com.v02.concurrency.stock.controller.ReqStockUseOnceParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class StockRabbitService {
    private final RabbitTemplate rabbitTemplate;

    /*
        CHECKPOINT 2
    */
    public void useStockOnce(ReqStockUseOnceParam param){
        rabbitTemplate.convertAndSend("stock.exchange.first","stock.bind.first",param);
    }

}
