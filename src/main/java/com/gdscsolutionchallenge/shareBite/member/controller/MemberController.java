package com.gdscsolutionchallenge.shareBite.member.controller;

import com.gdscsolutionchallenge.shareBite.member.dto.CreateMemberDto;
import com.gdscsolutionchallenge.shareBite.member.dto.UpdateMemberDto;
import com.gdscsolutionchallenge.shareBite.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/members")
public class MemberController {
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity createMember(@RequestBody CreateMemberDto createMemberDto) {
        memberService.createMember(createMemberDto);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity updateMember(@AuthenticationPrincipal UserDetails userDetails,
                                       @RequestBody UpdateMemberDto updateMemberDto) {
        memberService.updateMember(userDetails.getUsername(), updateMemberDto);

        return new ResponseEntity(HttpStatus.OK);
    }
}
