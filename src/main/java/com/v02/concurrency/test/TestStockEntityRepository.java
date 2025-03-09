package com.v02.concurrency.test;


import com.v02.concurrency.stock.repository.entity.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TestStockEntityRepository extends JpaRepository<StockEntity, Long> {
    StockEntity findByName(@Param("name") String name);
}
