package com.v02.concurrency.stock;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "t_stock")
public class StockEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(length = 77)
    private String name;

    private Long quantity;

    public StockEntity(Long quantity) {
        this.quantity = quantity;
    }



}
