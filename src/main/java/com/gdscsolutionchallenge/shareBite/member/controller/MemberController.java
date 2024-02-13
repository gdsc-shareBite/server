package com.gdscsolutionchallenge.shareBite.member.controller;

import com.gdscsolutionchallenge.shareBite.member.dto.CreateMemberDto;
import com.gdscsolutionchallenge.shareBite.member.dto.FindMemberDto;
import com.gdscsolutionchallenge.shareBite.member.dto.UpdateMemberDto;
import com.gdscsolutionchallenge.shareBite.member.dto.UpdateMemberRoleDto;
import com.gdscsolutionchallenge.shareBite.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/members")
public class MemberController {
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<?> createMember(@RequestPart(value = "createMemberDto") CreateMemberDto createMemberDto,
                                          @RequestPart(value = "imageFiles") List<MultipartFile> imageFiles) throws IOException {
        memberService.createMember(createMemberDto, imageFiles);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateMember(@AuthenticationPrincipal UserDetails userDetails,
                                          @RequestPart(value = "updateMemberDto") UpdateMemberDto updateMemberDto,
                                          @RequestPart(value = "imageFiles") List<MultipartFile> imageFiles) throws IOException {
        Long memberId = Long.parseLong(userDetails.getUsername());
        memberService.updateMember(memberId, updateMemberDto, imageFiles);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{memberId}/roles")
    public ResponseEntity<?> updateMemberRole(@PathVariable Long memberId,
                                              @RequestBody UpdateMemberRoleDto updateMemberRoleDto) {
        memberService.updateMemberRole(memberId, updateMemberRoleDto.getRole());

        return new ResponseEntity<> (HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<FindMemberDto> findMember(@AuthenticationPrincipal UserDetails userDetails) {
        Long memberId = Long.parseLong(userDetails.getUsername());

        return new ResponseEntity<>(memberService.findMember(memberId), HttpStatus.OK);
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<?> deleteMember(@PathVariable Long memberId) {
        memberService.deleteMember(memberId);

        return new ResponseEntity<> (HttpStatus.OK);
    }
}
