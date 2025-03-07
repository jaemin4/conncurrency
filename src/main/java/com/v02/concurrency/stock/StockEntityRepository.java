package com.v02.concurrency.stock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockEntityRepository extends JpaRepository<StockEntity, Long> {
}
