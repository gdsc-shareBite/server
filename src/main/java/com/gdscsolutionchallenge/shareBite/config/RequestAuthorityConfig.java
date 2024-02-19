//package com.gdscsolutionchallenge.shareBite.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class RequestAuthorityConfig extends SecurityConfigurerAdapter<SecurityFilterChain, HttpSecurity> {
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http
//                // STORE_OWNER
//                .authorizeRequests()
//                .antMatchers("/api/v1/posts/**").hasAnyRole("STORE_OWNER", "ADMIN")
//                .antMatchers(HttpMethod.GET, "/api/v1/tags").hasAnyRole("STORE_OWNER", "ADMIN")
//
//                // ADMIN
//                .antMatchers(HttpMethod.POST, "/api/v1/tags").hasRole("ADMIN")
//                .antMatchers(HttpMethod.PUT, "/api/v1/tags").hasRole("ADMIN")
//                .antMatchers(HttpMethod.DELETE, "/api/v1/tags").hasRole("ADMIN")
//                .antMatchers("/api/v1/members/black-list").hasRole("ADMIN")
//                .antMatchers("/api/v1/members/*/roles").hasRole("ADMIN")
//                .antMatchers("/api/v1/stores/*/status").hasRole("ADMIN")
//
//                .anyRequest().authenticated();
//    }
//}
