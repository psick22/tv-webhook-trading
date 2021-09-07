package me.tvhook.tvwebhook.domain.user;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import me.tvhook.tvwebhook.domain.order.OrderDto;

@Data
public class UserDto {

    private String email;
    private String name;
    private String pwd;
    private String userId;

    private String encryptedPwd;

    private List<OrderDto> orders;

}
