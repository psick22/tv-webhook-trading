package me.tvhook.tvwebhook.domain.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequestDto {

    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;

}
