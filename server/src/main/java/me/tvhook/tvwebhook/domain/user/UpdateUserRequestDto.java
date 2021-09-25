package me.tvhook.tvwebhook.domain.user;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class UpdateUserRequestDto {

    private BigDecimal allocatedKrw;
    private String upbitApiKey;
    private String upbitSecretKey;

}
