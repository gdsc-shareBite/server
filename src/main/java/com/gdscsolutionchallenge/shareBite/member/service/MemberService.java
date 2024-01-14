package com.gdscsolutionchallenge.shareBite.member.service;

import com.gdscsolutionchallenge.shareBite.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void logout() {

    }

}