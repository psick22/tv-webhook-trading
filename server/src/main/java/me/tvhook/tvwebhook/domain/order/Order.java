package me.tvhook.tvwebhook.domain.order;


import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.tvhook.tvwebhook.common.enums.OrderSide;
import me.tvhook.tvwebhook.common.enums.OrderStatus;
import me.tvhook.tvwebhook.common.enums.OrderType;
import me.tvhook.tvwebhook.domain.trade.Trade;
import me.tvhook.tvwebhook.domain.user.User;

@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    private String uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    @OneToMany(mappedBy = "order")
    private List<Trade> trades = new ArrayList<>();
    private Long tradeCount;


    private String market;


    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "ord_type")
    private OrderType type;

    @Enumerated(EnumType.STRING)
    private OrderSide side;
}
