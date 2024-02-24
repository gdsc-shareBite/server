package com.gdscsolutionchallenge.shareBite.auth.controller;

import com.gdscsolutionchallenge.shareBite.auth.service.AuthService;
import com.gdscsolutionchallenge.shareBite.auth.dto.TokensDto;
import com.gdscsolutionchallenge.shareBite.auth.dto.LoginDto;
import com.gdscsolutionchallenge.shareBite.config.jwt.TokenProvider;
import com.gdscsolutionchallenge.shareBite.exception.code.ErrorCode;
import com.gdscsolutionchallenge.shareBite.exception.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final TokenProvider tokenProvider;

    @Value("test")
    private List<String> testList;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto, HttpServletResponse response) {

        System.out.println(testList);

        TokensDto tokensDto = authService.login(loginDto);

        Cookie accessTokenCookie = new Cookie("accessToken", tokensDto.getAccessToken());
        accessTokenCookie.setMaxAge((int) tokenProvider.getATK_EXPIRATION());
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setPath("/api/");
        response.addCookie(accessTokenCookie);

        Cookie refreshTokenCookie = new Cookie("refreshToken", tokensDto.getRefreshToken());
        refreshTokenCookie.setMaxAge((int) tokenProvider.getRTK_EXPIRATION());
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/api/");
        response.addCookie(refreshTokenCookie);

        return new ResponseEntity<> (HttpStatus.OK);
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> tokenReissue(@CookieValue(name = "refreshToken", required = false) String refreshToken, HttpServletResponse response) {
        if(refreshToken == null) {
            throw new BadRequestException(ErrorCode.BAD_REQUEST, "Refresh token missing. Please include it in your request");
        }

        TokensDto tokensDto = authService.tokenReissue(refreshToken);

        Cookie accessTokenCookie = new Cookie("accessToken", tokensDto.getAccessToken());
        accessTokenCookie.setMaxAge((int) tokenProvider.getATK_EXPIRATION());
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setPath("/api/");

        response.addCookie(accessTokenCookie);

        Cookie refreshTokenCookie = new Cookie("refreshToken", tokensDto.getRefreshToken());
        refreshTokenCookie.setMaxAge((int) tokenProvider.getRTK_EXPIRATION());
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/api/");
        response.addCookie(refreshTokenCookie);

        return new ResponseEntity<> (HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@CookieValue(name = "accessToken", required = false) String accessToken) {
        authService.logout(accessToken);

        return new ResponseEntity<> (HttpStatus.OK);
    }

    @PostMapping({"/black-list/{memberId}"})
    public ResponseEntity<?> setBlackList(@PathVariable Long memberId) {
        authService.setBlackList(memberId);

        return new ResponseEntity<> (HttpStatus.OK);
    }
}
