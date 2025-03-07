package com.v02.concurrency;


import com.v02.concurrency.stock.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
@Slf4j
public class StockTest {

    @Autowired
    private StockService stockService;

    @Autowired
    private StockEntityRepository stockEntityRepository;

    @BeforeEach
    void setUp(){
        stockEntityRepository.save(new StockEntity("test",100L));
    }
    @AfterEach
    void tearDown(){
        StockEntity stockEntity = stockEntityRepository.findByName("test");
        stockEntityRepository.delete(stockEntity);
    }
    @Test
    @DisplayName("재고 감소 테스트")
    void decrease(){
        stockService.useStockOnce(new ReqStockUseOnceParam("test"));
        final StockEntity stock = stockEntityRepository.findByName("test");

        assertThat(stock.getQuantity()).isEqualTo(99);
    }


    @Test
    @DisplayName("동시에 100개의 요처으로 재고를 감소.")
    void decrease_100_request() throws InterruptedException{
        final int threadCount = 100;
        final ExecutorService executorService = Executors.newFixedThreadPool(32);
        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for(int i = 0; i < threadCount; i++){
            executorService.submit(() -> {
                try {
                    stockService.useStockOnce(new ReqStockUseOnceParam("test"));
                }finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        final StockEntity stock = stockEntityRepository.findByName("test");
        log.info("데이터 : {}", stock.getQuantity());
        assertThat(stock.getQuantity()).isEqualTo(0);
    }

    @Test
    void contextLoads() {
    }



}
