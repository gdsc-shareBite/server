package com.gdscsolutionchallenge.shareBite.auth.service;

import com.gdscsolutionchallenge.shareBite.auth.dto.LoginDto;
import com.gdscsolutionchallenge.shareBite.config.jwt.TokenProvider;
import com.gdscsolutionchallenge.shareBite.auth.dto.TokensDto;
import com.gdscsolutionchallenge.shareBite.config.redis.RedisService;
import com.gdscsolutionchallenge.shareBite.exception.code.ErrorCode;
import com.gdscsolutionchallenge.shareBite.exception.exceptions.BadRequestException;
import com.gdscsolutionchallenge.shareBite.exception.exceptions.ResourceNotFoundException;
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
@Service
public class AuthService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RedisService redisService;

    @Transactional
    public TokensDto login(LoginDto loginDto) {
        Optional<Member> optionalMember = memberRepository.findByName(loginDto.getName());
        Member member = verifyMember(optionalMember);

        if(!passwordEncoder.matches(loginDto.getPassword(), member.getPassword())) {
            throw new BadRequestException(ErrorCode.BAD_REQUEST, "Incorrect password. Please check your password and try again.");
        }

        String memberId = String.valueOf(member.getMemberId());

        UserDetails userDetails = User.builder()
                .username(memberId)
                .password(member.getPassword())
                .authorities(member.getRole().toString())
                .build();

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = tokenProvider.createAccessToken(memberId, member.getRole().name());
        String refreshToken = tokenProvider.createRefreshToken(memberId);

        redisService.saveRefreshToken(memberId, refreshToken, tokenProvider.getRTK_EXPIRATION());
        redisService.deleteAccessToken(memberId);

        return new TokensDto(accessToken, refreshToken);
    }

    @Transactional
    public void logout(String accessToken) {
        String memberId = tokenProvider.getAuthentication(accessToken).getName();
        Long expiration = tokenProvider.getRemainExpiration(accessToken);
        redisService.saveAccessToken(memberId, accessToken, expiration);

        redisService.deleteRefreshToken(memberId);
    }

    @Transactional
    public TokensDto tokenReissue(String oldRefreshToken) {
        Authentication authentication = tokenProvider.getAuthentication(oldRefreshToken);
        String memberId = authentication.getName();
        Member member = verifyMember(Long.parseLong(memberId));

        String accessToken = tokenProvider.createAccessToken(memberId, member.getRole().name());
        String newRefreshToken = tokenProvider.createRefreshToken(memberId);
        redisService.saveRefreshToken(memberId, newRefreshToken, tokenProvider.getRTK_EXPIRATION());

        return new TokensDto(accessToken, newRefreshToken);
    }

    @Transactional
    public void setBlackList(Long memberId) {
        verifyMember(memberId);

        redisService.deleteRefreshToken(String.valueOf(memberId));
    }

    private Member verifyMember(Optional<Member> optionalMember) {
        return optionalMember.orElseThrow(() -> new ResourceNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }

    private Member verifyMember(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }

}