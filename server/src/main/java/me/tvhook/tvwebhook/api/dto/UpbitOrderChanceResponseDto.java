package me.tvhook.tvwebhook.api.dto;

import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpbitOrderChanceResponseDto {

    private BigDecimal bid_fee;
    private BigDecimal ask_fee;
    private UpbitMarketDto market;
    private UpbitAccountDto bid_account;
    private UpbitAccountDto ask_account;
}
