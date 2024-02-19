package com.gdscsolutionchallenge.shareBite.config.jwt;

import io.jsonwebtoken.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class TokenProvider {
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.atk_expiration}")
    private long ATK_EXPIRATION;

    @Getter
    @Value("${jwt.rtk_expiration}")
    private long RTK_EXPIRATION;

    @PostConstruct
    public void encodeBase64SecretKey() {
        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public String createAccessToken(String memberId, String role) {
        Claims claims = Jwts.claims().setSubject(memberId);
        claims.put("role", role);

        Date now = new Date();
        Date expiration = new Date(now.getTime() + ATK_EXPIRATION);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String createRefreshToken(String memberId) {
        Claims claims = Jwts.claims().setSubject(memberId);
        Date now = new Date();
        Date expiration = new Date(now.getTime() + RTK_EXPIRATION);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaim(token);
        String memberId = claims.getSubject();
        String role = claims.get("role", String.class);
        String AUTHORITY_PREFIX = "ROLE_";
        List<GrantedAuthority> authorities =  Collections.singletonList(new SimpleGrantedAuthority(AUTHORITY_PREFIX + role));

        UserDetails userDetails = new User(memberId, "", authorities);

        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

    public Long getRemainExpiration(String token) {
        Claims claims = getClaim(token);
        Date now = new Date();
        Date expiration = claims.getExpiration();

        return expiration.getTime() - now.getTime();
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = getClaim(token);
            Date now = new Date();

            return claims.getExpiration()
                    .after(now);

        } catch (ExpiredJwtException e) {
            log.error("JWT verification failed: Token expired");

        } catch (MalformedJwtException e) {
            log.error("JWT verification failed: Malformed token");

        } catch (SignatureException e) {
            log.error("JWT verification failed: Signature verification failed");

        } catch (UnsupportedJwtException e) {
            log.error("JWT verification failed: Unsupported JWT");

        } catch (IllegalArgumentException e) {
            log.error("JWT verification failed: Illegal argument");
        }
        return false;
    }

    public Claims getClaim(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token).getBody();
    }

    public String extractAccessToken(String bearerToken) {
        String BEARER_PREFIX = "Bearer";

        if (bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        return null;
    }
}
