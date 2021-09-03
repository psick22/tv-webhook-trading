package me.tvhook.tvwebhook.api.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpbitMarketDto {

    private String id;
    private String name;
    private List<String> order_types;
    private List<String> order_sides;
    private UpbitAskBidConstraintsDto bid;
    private UpbitAskBidConstraintsDto ask;
    private BigDecimal max_total;
    private String state;
}
