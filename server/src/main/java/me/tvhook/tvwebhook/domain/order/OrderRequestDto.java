package me.tvhook.tvwebhook.domain.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import me.tvhook.tvwebhook.common.enums.OrderSide;
import me.tvhook.tvwebhook.common.enums.OrderType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {

    public Long userId;
    public String market;
    public OrderSide side;
    public String volume;
    public String price;
    public OrderType orderType;

}
