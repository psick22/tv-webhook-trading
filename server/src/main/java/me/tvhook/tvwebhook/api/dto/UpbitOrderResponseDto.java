package me.tvhook.tvwebhook.api.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.tvhook.tvwebhook.domain.trade.Trade;


@Data
@NoArgsConstructor
public class UpbitOrderResponseDto {

    private String uuid;
    private String side;
    private String ord_type;
    private BigDecimal price;
    private String state;
    private String market;
    private String created_at;
    private BigDecimal volume;
    private BigDecimal remaining_volume;
    private BigDecimal remaining_fee;
    private BigDecimal reserved_fee;
    private BigDecimal paid_fee;
    private BigDecimal locked;
    private BigDecimal executed_volume;
    private Long trades_count;
    private List<Trade> trades;
}

