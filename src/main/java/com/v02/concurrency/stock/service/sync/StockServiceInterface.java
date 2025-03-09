package com.v02.concurrency.stock.service.sync;

import com.v02.concurrency.response.RestResult;
import com.v02.concurrency.stock.controller.ReqStockSetParam;
import com.v02.concurrency.stock.controller.ReqStockUseOnceParam;


public interface StockServiceInterface {
    RestResult setStockQuantity(ReqStockSetParam param);

    RestResult useStockOnce(ReqStockUseOnceParam param);
}
