package com.gdscsolutionchallenge.shareBite.config.jwt;

import com.gdscsolutionchallenge.shareBite.config.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;
    private final RedisService redisService;

    @Value("${jwt.reissue_Url}")
    private String reissueUrl;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getRequestURI().equals(reissueUrl)) {
            String refreshToken = request.getHeader("X-Refresh-Token");

            if (refreshToken != null && tokenProvider.validateToken(refreshToken)) {
                Authentication authentication = tokenProvider.getAuthentication(refreshToken);

                if (authentication != null) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } else {
            String accessToken = tokenProvider.extractAccessToken(request.getHeader("Authorization"));

            if(accessToken != null && tokenProvider.validateToken(accessToken)) {
                Authentication authentication = tokenProvider.getAuthentication(accessToken);

                if(authentication != null) {
                    String memberId = authentication.getName();

                    if(redisService.getAccessToken(memberId).isPresent()) {
                        logger.info("black-list로 등록된 토큰입니다.");

                    } else {
                        SecurityContextHolder.getContext().setAuthentication(authentication);

                    }
                }
            }
        }

        filterChain.doFilter(request, response);
    }

}