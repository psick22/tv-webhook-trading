package me.tvhook.tvwebhook.api.dto;

import java.util.List;
import javax.persistence.OrderBy;
import javax.swing.plaf.nimbus.State;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.tvhook.tvwebhook.common.enums.OrderStatus;
import org.hibernate.criterion.Order;

@Data
@NoArgsConstructor
public class UpbitOrderRequestDto {

    private String market;
    private OrderStatus state;
    private List<OrderStatus> states;
    private List<String> uuids;
    private String identifiers;
    private int page;
    private int limit;
    private String order_by;

}
