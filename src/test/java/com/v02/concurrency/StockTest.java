package com.v02.concurrency;


import com.v02.concurrency.stock.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
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
        stockEntityRepository.delete(new StockEntity("test"));
    }
    @Test
    @DisplayName("재고 감소 테스트")
    void decrease(){
        stockService.useStockOnce(new ReqStockUseOnceParam("test"));
        final StockEntity stock = stockEntityRepository.findByName("test");

        assertThat(stock.getQuantity()).isEqualTo(98);
    }






    @Test
    void contextLoads() {
    }



}
