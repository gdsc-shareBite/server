package com.gdscsolutionchallenge.shareBite.config;

import com.gdscsolutionchallenge.shareBite.config.jwt.JwtConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity(debug = false)
@Configuration
public class SecurityConfig {
    private final JwtConfig jwtConfig;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .headers().frameOptions().sameOrigin() // h2
                .and()
                .cors().and()
                .csrf().disable()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .authorizeRequests()
                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/favicon.ico", "/h2-console/**").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/v1/members", "/api/v1/auth/login").permitAll()
                    .antMatchers("/api/v1/members/black-list").hasAuthority("ROLE_ADMIN")
                    .anyRequest().authenticated()
                .and()
                    .apply(jwtConfig)
                .and()
                    .logout()
                        .logoutSuccessUrl("/");

        return http.build();
    }

}
