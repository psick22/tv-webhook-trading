package me.tvhook.tvwebhook.domain.webhook;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;

@Entity
public class Webhook {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private WebhookType type;

    private String username;

    private LocalDateTime candleTime;
    private LocalDateTime time;

    private String ticker; //BTCKRW
    private String exchange;
    private String timeframe;

    private BigDecimal bidAmount; // KRW
    private BigDecimal askRate; // %

    private String strategyName;
    private String orderAction;
    private String orderPrice;
    private String OrderId;


}