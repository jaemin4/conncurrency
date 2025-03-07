package com.v02.concurrency.stock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockEntityRepository extends JpaRepository<StockEntity, Long> {
    StockEntity findByName(String name);
}
