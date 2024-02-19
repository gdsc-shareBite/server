package com.gdscsolutionchallenge.shareBite.auth.controller;

import com.gdscsolutionchallenge.shareBite.auth.service.AuthService;
import com.gdscsolutionchallenge.shareBite.auth.dto.TokensDto;
import com.gdscsolutionchallenge.shareBite.auth.dto.LoginDto;
import com.gdscsolutionchallenge.shareBite.config.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final TokenProvider tokenProvider;
    private final String AUTHORIZATION_HEADER = "Authorization";
    private final String REFRESH_TOKEN_HEADER = "X-Refresh-Token";
    private final String BEARER_TOKEN_PREFIX = "Bearer ";

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {

        TokensDto tokensDto = authService.login(loginDto);

        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION_HEADER, BEARER_TOKEN_PREFIX + tokensDto.getAccessToken());
        headers.add(REFRESH_TOKEN_HEADER, tokensDto.getRefreshToken());

        return ResponseEntity.ok()
                .headers(headers)
                .build();
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> tokenReissue(@RequestHeader(REFRESH_TOKEN_HEADER) String refreshToken) {
        TokensDto tokensDto = authService.tokenReissue(refreshToken);

        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION_HEADER, BEARER_TOKEN_PREFIX + tokensDto.getAccessToken());
        headers.add(REFRESH_TOKEN_HEADER, tokensDto.getRefreshToken());

        return ResponseEntity.ok()
                .headers(headers)
                .build();
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader(AUTHORIZATION_HEADER) String bearerToken) {
        String accessToken =  tokenProvider.extractAccessToken(bearerToken);
        authService.logout(accessToken);

        return new ResponseEntity<> (HttpStatus.OK);
    }

    @PostMapping({"/black-list/{memberId}"})
    public ResponseEntity<?> setBlackList(@PathVariable Long memberId) {
        authService.setBlackList(memberId);

        return new ResponseEntity<> (HttpStatus.OK);
    }
}
