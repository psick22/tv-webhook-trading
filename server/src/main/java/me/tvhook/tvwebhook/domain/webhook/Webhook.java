package me.tvhook.tvwebhook.domain.webhook;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString
@Getter
@Setter
public class Webhook {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime time;

    private LocalDateTime candleTime;

    private String ticker;

    private String exchange;

    private String orderAction;

    private String orderId;

    private String orderPrice;

    private String message;


}

/**
 * { "time":{{timenow}}, "ticker":{{ticker}}, "exchange":{{exchange}}, "candleTime":{{time}},
 * "orderAction" :{{strategy.order.action}}, "message":{{strategy.order.alert_message}},
 * "orderId":{{strategy.order.id}}, "orderPrice":{{strategy.order.price}} }
 */