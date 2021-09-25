package me.tvhook.tvwebhook.domain.webhook;

import lombok.Data;

@Data
public class WebhookDto {

    private WebhookType type;
    private String username;
    private String candleTime;
    private String time;
    private String exchange;
    private String ticker;
    private String interval;

    private String strategyName;
    private String orderAction;
    private String orderId;
    private String orderPrice;


    private String bidRate;
    private String askRate;

    public WebhookDto(WebhookType type, String username, String strategyName,
        String bidRate, String askRate) {
        this.type = type;
        this.username = username;
        this.strategyName = strategyName;
        this.bidRate = bidRate;
        this.askRate = askRate;
        this.candleTime = "{{time}}";
        this.time = "{{timenow}}";
        this.exchange = "{{exchange}}";
        this.ticker = "{{ticker}}";
        this.interval = "{{interval}}";
        this.orderAction = "{{strategy.order.action}}";
        this.orderId = "{{strategy.order.id}}";
        this.orderPrice = "{{strategy.order.price}}";
    }

    public static WebhookDto generateTemplate(WebhookType type, String username,
        String strategyName,
        String bidRate, String askRate) {

        return new WebhookDto(type, username, strategyName,
            bidRate, askRate);
    }
}
