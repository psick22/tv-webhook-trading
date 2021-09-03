package me.tvhook.tvwebhook.api.dto;

import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpbitAccountDto {

    private String currency;
    private BigDecimal balance; // 주문 가능 금액/수량
    private BigDecimal locked; // 주문 중 묶여있는 금액/수량
    private BigDecimal avg_buy_price; // 매수 평균가
    private boolean avg_buy_price_modified; // 매수평균가 수정 여부
    private String unit_currency; //평단가 기준 화폐


}
