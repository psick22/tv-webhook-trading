package me.tvhook.tvwebhook.domain.webhook;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class Strategy {

    private String name;
    private String orderAction;
    private BigDecimal orderPrice;
    private PositionType OrderId;

}
