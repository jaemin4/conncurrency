package com.v02.rabbit.stock;

import com.v02.rabbit.request.ReqStockUseOnceParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConsumerService {

    private final RabbitStockRepository rabbitStockRepository;


    /*
        CHECKPOINT 3
    */
    @RabbitListener(queues = "stock.queue.first", containerFactory = "rabbitListenerContainerFactory")
    @Transactional
    public void useStockOnce(ReqStockUseOnceParam param) {
        try {
            StockEntity findEntity = rabbitStockRepository.findByName(param.getName())
                    .orElse(null);

            if (findEntity == null) {
                log.warn("Stock not found for name: {}", param.getName());
                return; // ✅ 재고가 없으면 처리를 종료
            }

            if (findEntity.getQuantity() <= 0) {
                log.warn("Stock is already zero: {}", param.getName());
                return; // ✅ 재고가 0이면 처리를 종료
            }

            findEntity.setQuantity(findEntity.getQuantity() - 1);
            rabbitStockRepository.save(findEntity);
            log.info("--- == save complete == ---");

        } catch (Exception e) {
            log.error("예외 발생: {}", e.getMessage());
        }
    }

}
