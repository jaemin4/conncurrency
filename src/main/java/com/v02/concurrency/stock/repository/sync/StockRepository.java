package com.v02.concurrency.stock.repository.sync;

import com.v02.concurrency.stock.repository.entity.StockEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<StockEntity, Long> {
    StockEntity findByName(String name);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from StockEntity s where s.name = :name")
    StockEntity findByNameWithPessimisticLock(@Param("name") String name);

}
