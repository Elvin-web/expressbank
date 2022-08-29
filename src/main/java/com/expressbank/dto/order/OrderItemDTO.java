package com.expressbank.dto.order;

import com.expressbank.entity.OrderEntity;
import com.expressbank.entity.StockEntity;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class OrderItemDTO {
    private Integer id;
    private Double quantity;
    private StockEntity stockEntity;


    public OrderItemDTO(OrderEntity orderEntity) {
        this.id = orderEntity.getId();
        this.quantity = orderEntity.getQuantity();
        this.stockEntity = orderEntity.getStockEntity();
    }
}
