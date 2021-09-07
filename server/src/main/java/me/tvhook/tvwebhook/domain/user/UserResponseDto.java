package me.tvhook.tvwebhook.domain.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.Data;
import me.tvhook.tvwebhook.domain.order.Order;
import me.tvhook.tvwebhook.domain.order.OrderDto;

@Data
@JsonInclude(Include.NON_NULL)
public class UserResponseDto {

    private Long id;
    private String email;
    private String username;

    private List<OrderDto> orders;
}
