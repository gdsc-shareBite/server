package com.gdscsolutionchallenge.shareBite.member.service;

import com.gdscsolutionchallenge.shareBite.config.jwt.TokenProvider;
import com.gdscsolutionchallenge.shareBite.config.redis.RedisService;
import com.gdscsolutionchallenge.shareBite.member.dto.CreateMemberDto;
import com.gdscsolutionchallenge.shareBite.member.dto.UpdateMemberDto;
import com.gdscsolutionchallenge.shareBite.member.entity.Member;
import com.gdscsolutionchallenge.shareBite.member.repository.MemberRepository;
import com.gdscsolutionchallenge.shareBite.member.state.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RedisService redisService;

    public void createMember(CreateMemberDto createMemberDto) {
        String memberName = createMemberDto.getName();

        if(memberRepository.findByName(memberName).isPresent()) {
            // 이미 존재하는 name입니다
        }

        Member member = Member.builder()
                .name(memberName)
                .password(passwordEncoder.encode(createMemberDto.getPassword()))
                .email(createMemberDto.getEmail())
                .country(createMemberDto.getCountry())
                .address(createMemberDto.getAddress())
                .role(Role.MEMBER)
                .build();

        memberRepository.save(member);
    }

    public void updateMember(String memberName, UpdateMemberDto updateMemberDto) {
        Member member = verifyMember(memberRepository.findByName(memberName));
        member.update(updateMemberDto.getPassword(),
                updateMemberDto.getName(),
                updateMemberDto.getEmail(),
                updateMemberDto.getProfileImageUrl(),
                updateMemberDto.getCountry(),
                updateMemberDto.getAddress());
    }

    private Member verifyMember(Optional<Member> optionalMember) {
        return optionalMember.orElseThrow(() -> new RuntimeException());
    }

}