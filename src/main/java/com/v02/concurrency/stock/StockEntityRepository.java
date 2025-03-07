package com.v02.concurrency.stock;

import jakarta.persistence.LockModeType;
import org.apache.catalina.authenticator.SpnegoAuthenticator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockEntityRepository extends JpaRepository<StockEntity, Long> {
    StockEntity findByName(String name);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from StockEntity s where s.name = :name")
    StockEntity findByNameWithPessimisticLock(@Param("name") String name);
}
