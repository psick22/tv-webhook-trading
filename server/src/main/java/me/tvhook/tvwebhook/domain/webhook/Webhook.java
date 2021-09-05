package me.tvhook.tvwebhook.domain.webhook;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
public class Webhook {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private LocalDateTime candleTime;

    private LocalDateTime time;

    private String ticker; //BTCKRW

    private String exchange;
    private String interval;
    private Strategy strategy;


}

/**
 * { "time":{{timenow}}, "ticker":{{ticker}}, "exchange":{{exchange}}, "candleTime":{{time}},
 * "orderAction" :{{strategy.order.action}}, "message":{{strategy.order.alert_message}},
 * "orderId":{{strategy.order.id}}, "orderPrice":{{strategy.order.price}} }
 */