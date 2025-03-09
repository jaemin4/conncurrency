package com.v02.concurrency.stock.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "t_stock")
@NoArgsConstructor
@AllArgsConstructor
public class StockEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;



    @Column(length = 77,unique = true)
    private String name;

    private Long quantity;

    public StockEntity(Long quantity) {
        this.quantity = quantity;
    }

    public StockEntity(String name) {
        this.name = name;
    }

    public StockEntity(String name, Long quantity) {
        this.name = name;
        this.quantity = quantity;
    }

}
