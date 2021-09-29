package me.tvhook.tvwebhook.domain.order;

import com.querydsl.core.annotations.QueryProjection;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.tvhook.tvwebhook.common.enums.OrderSide;
import me.tvhook.tvwebhook.common.enums.OrderStatus;
import me.tvhook.tvwebhook.common.enums.OrderType;

@Data
@NoArgsConstructor
public class OrderDto {

    private Long id;
    private String uuid;
    private String market;
    private OrderStatus status;
    private OrderType type;
    private OrderSide side;
    private boolean open;
    private BigDecimal funds;

    @Builder
    @QueryProjection
    public OrderDto(Long id, String uuid, String market,
        OrderStatus status, OrderType type, OrderSide side) {
        this.id = id;
        this.uuid = uuid;
        this.market = market;
        this.status = status;
        this.type = type;
        this.side = side;
    }
}
