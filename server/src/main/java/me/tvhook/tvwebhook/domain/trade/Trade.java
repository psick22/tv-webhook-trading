package me.tvhook.tvwebhook.domain.trade;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import me.tvhook.tvwebhook.common.enums.OrderSide;
import me.tvhook.tvwebhook.domain.order.Order;

@Entity
@Getter
@Setter
public class Trade {

    @Id
    @GeneratedValue
    @Column(name = "trade_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private BigDecimal price;

    private BigDecimal volume;
    private BigDecimal funds;

    @Enumerated(EnumType.STRING)
    private OrderSide side;

    private LocalDateTime createdAt;


}
