package me.tvhook.tvwebhook.domain.order;

import java.util.List;

public interface OrderRepositoryCustom {
    List<OrderDto> findAllByConditions(OrderSearchDto conditions);

}
