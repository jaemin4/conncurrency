package com.v02.rabbit.stock;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RabbitStockRepository extends JpaRepository<StockEntity, Long> {

    Optional<StockEntity> findByName(String name);


}
