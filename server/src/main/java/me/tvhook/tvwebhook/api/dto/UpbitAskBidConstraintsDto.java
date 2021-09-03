package me.tvhook.tvwebhook.api.dto;

import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpbitAskBidConstraintsDto {

    private String currency; // 화폐 코드
    private String price_unit; // 주문 금액 단위
    private BigDecimal min_total; // 최대 매도/매수 금액

}
