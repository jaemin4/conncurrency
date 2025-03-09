package com.v02.concurrency;


import com.v02.concurrency.stock.controller.ReqStockUseOnceParam;
import com.v02.concurrency.stock.repository.entity.StockEntity;
import com.v02.concurrency.stock.repository.sync.StockRepository;
import com.v02.concurrency.stock.service.asyn.StockRabbitService;
import com.v02.concurrency.stock.service.sync.StockMainService;
import com.v02.concurrency.stock.service.sync.StockPessimisticService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {ConcurrencyApplication.class})
@Slf4j
@ActiveProfiles("main")
public class StockMainServiceTest {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockMainService stockMainService;

    @Autowired
    StockPessimisticService stockPessimisticService;

    @Autowired
    StockRabbitService stockRabbitService;

    @BeforeEach
    void setUp(){
        stockRepository.save(new StockEntity("test",100L));
    }
    @AfterEach
    void tearDown(){
        StockEntity stockEntity = stockRepository.findByName("test");
        stockRepository.delete(stockEntity);
    }
    @Test
    @DisplayName("재고 감소 테스트")
    void decrease(){
        stockMainService.useStockOnce(new ReqStockUseOnceParam("test"));
        final StockEntity stock = stockRepository.findByName("test");

        assertThat(stock.getQuantity()).isEqualTo(99);
    }
    @Test
    @DisplayName("기존로직 동시성 테스트")
    void mainTest() throws InterruptedException{
        decrease_request("test","main",100, 0L);

        //  결과/실패
        //  Expected :0L
        //  Actual   :80L
    }

    @Test
    @DisplayName("비관적락 동시성 테스트")
    void pessimisticServiceTest() throws InterruptedException{
       decrease_request("test","stockPessimisticService",100, 0L);
        //  결과/성공
    }

    @Test
    @DisplayName("RabbitMq 동시성 테스트")
    void RabbitmqTest() throws InterruptedException{
        decrease_request("test","rabbitmq",100,0L);
        //  결과/성공
    }
    void decrease_request(String name, String type, Integer requestNum, Long expectedNumb) throws InterruptedException{
        final int threadCount = requestNum;
        final ExecutorService executorService = Executors.newFixedThreadPool(32);
        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        ReqStockUseOnceParam param = new ReqStockUseOnceParam(name);

        for(int i = 0; i < threadCount; i++){
            executorService.submit(() -> {
                try {
                    switch (type)
                    {
                        case "stockPessimisticService" -> stockPessimisticService.useStockOnce(param);
                        case "main" -> stockMainService.useStockOnce(param);
                        case "rabbitmq" -> stockRabbitService.useStockOnce(param);
                    }

                }finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        executorService.shutdown(); // 스레드 풀 종료

        final StockEntity stock = stockRepository.findByName(name);
        log.info("데이터 : {}", stock.getQuantity());
        assertThat(stock.getQuantity()).isEqualTo(expectedNumb);

    }

    @Test
    void contextLoads() {
    }



}
