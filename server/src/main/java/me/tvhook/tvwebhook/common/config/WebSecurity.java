package me.tvhook.tvwebhook.common.config;

import lombok.RequiredArgsConstructor;
import me.tvhook.tvwebhook.domain.user.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final BCryptPasswordEncoder encoder;

    /**
     * 권한
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.antMatcher("/**").addFilter(getAuthenticationFilter());
    }

    /**
     * 인증 (로그인 처리)
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(encoder);


    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception{
        AuthenticationFilter authFilter = new AuthenticationFilter();
        authFilter.setAuthenticationManager(authenticationManager());

        return authFilter;
    }

}

