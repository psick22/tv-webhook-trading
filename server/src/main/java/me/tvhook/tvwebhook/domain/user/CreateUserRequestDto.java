package me.tvhook.tvwebhook.domain.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateUserRequestDto {

    @NotNull(message="email은 필수 항목입니다")
    @Email
    private String email;

    @NotNull(message="username은 필수 항목입니다")
    private String username;

    @NotNull(message="username은 필수 항목입니다")
    private String pwd;

}
