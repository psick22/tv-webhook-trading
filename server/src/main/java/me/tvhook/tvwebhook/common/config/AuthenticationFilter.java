package me.tvhook.tvwebhook.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.tvhook.tvwebhook.domain.user.LoginRequestDto;
import me.tvhook.tvwebhook.domain.user.UserDto;
import me.tvhook.tvwebhook.domain.user.UserService;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final UserService userService;
    private final Environment env;


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response) throws AuthenticationException {

        ObjectMapper mapper = new ObjectMapper();

        try {
            LoginRequestDto credentials = mapper.readValue(request.getInputStream(),
                LoginRequestDto.class);

            return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                    credentials.getEmail(),
                    credentials.getPassword(),
                    new ArrayList<>())
            );

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, FilterChain chain, Authentication authResult)
        throws IOException, ServletException {

        String email = ((org.springframework.security.core.userdetails.User) authResult.getPrincipal()).getUsername();

        System.out.println("email = " + email);
        UserDto userDetails = userService.getUserDetailsByEmail(email);

        String token = Jwts.builder()
            .setSubject(userDetails.getUserId())
            .setExpiration(
                new Date(System.currentTimeMillis() +
                    Long.parseLong(env.getProperty("token.expiration_time"))))
            .signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret_key"))
            .compact();

        response.addHeader("token", token);
        response.addHeader("userId", userDetails.getUserId());

    }
}
