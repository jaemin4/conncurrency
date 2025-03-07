package com.v02.concurrency.stock;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReqStockSetParam {

    private Long quantity;
    private String name;
}
