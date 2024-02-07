package com.gdscsolutionchallenge.shareBite.auth.service;

import com.gdscsolutionchallenge.shareBite.auth.dto.LoginDto;
import com.gdscsolutionchallenge.shareBite.config.jwt.TokenProvider;
import com.gdscsolutionchallenge.shareBite.auth.dto.TokensDto;
import com.gdscsolutionchallenge.shareBite.config.redis.RedisService;
import com.gdscsolutionchallenge.shareBite.member.entity.Member;
import com.gdscsolutionchallenge.shareBite.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class AuthService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RedisService redisService;

    public TokensDto login(LoginDto loginDto) {
        Optional<Member> optionalMember = memberRepository.findByName(loginDto.getName());
        Member member = verifyMember(optionalMember);
        String memberName = member.getName();

        if(!passwordEncoder.matches(loginDto.getPassword(), member.getPassword())) {
            // 비밀번호 잘못 입력
        }

        UserDetails userDetails = User.builder()
                .username(member.getName())
                .password(member.getPassword())
                .authorities(member.getRole().toString())
                .build();

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);


        String accessToken = tokenProvider.createAccessToken(memberName, member.getRole().name());
        String refreshToken = tokenProvider.createRefreshToken(memberName);

        redisService.saveRefreshToken(memberName, refreshToken, tokenProvider.getRTK_EXPIRATION());

        return new TokensDto(accessToken, refreshToken);
    }

    public void logout(TokensDto tokensDto) {
        String refreshToken = tokensDto.getRefreshToken();
        redisService.deleteRefreshToken(refreshToken);

        String accessToken = tokensDto.getAccessToken();
        String memberName = tokenProvider.getAuthentication(accessToken).getName();
        Long expiration = tokenProvider.getRemainExpiration(accessToken);
        redisService.saveAccessToken(memberName, accessToken, expiration);
    }

    public TokensDto tokenReissue(String oldRefreshToken) {
        Authentication authentication = tokenProvider.getAuthentication(oldRefreshToken);
        String memberName = authentication.getName();
        Member member = verifyMember(memberRepository.findByName(memberName));

        String accessToken = tokenProvider.createAccessToken(memberName, member.getRole().name());
        String newRefreshToken = tokenProvider.createRefreshToken(memberName);
        redisService.saveRefreshToken(memberName, newRefreshToken, tokenProvider.getRTK_EXPIRATION());

        return new TokensDto(accessToken, newRefreshToken);
    }

//    public void setBlackList(String memberName) {
//        redisService.deleteRefreshToken(memberName);
//    }

    private Member verifyMember(Optional<Member> optionalMember) {
        return optionalMember.orElseThrow(() -> new RuntimeException());
    }

}