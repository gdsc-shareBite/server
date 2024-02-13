package com.gdscsolutionchallenge.shareBite.member.service;

import com.gdscsolutionchallenge.shareBite.exception.exceptions.ConflictException;
import com.gdscsolutionchallenge.shareBite.exception.code.ErrorCode;
import com.gdscsolutionchallenge.shareBite.gcs.service.GcsService;
import com.gdscsolutionchallenge.shareBite.gcs.state.ImageType;
import com.gdscsolutionchallenge.shareBite.member.dto.CreateMemberDto;
import com.gdscsolutionchallenge.shareBite.member.dto.FindMemberDto;
import com.gdscsolutionchallenge.shareBite.member.dto.UpdateMemberDto;
import com.gdscsolutionchallenge.shareBite.member.entity.Member;
import com.gdscsolutionchallenge.shareBite.member.repository.MemberRepository;
import com.gdscsolutionchallenge.shareBite.member.state.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final GcsService gcsService;

    @Transactional
    public void createMember(CreateMemberDto createMemberDto, List<MultipartFile> imageFiles) throws IOException {
        String memberName = createMemberDto.getName();
        String memberEmail = createMemberDto.getEmail();

        if(memberRepository.existsByName(memberName)) {
            throw new ConflictException(ErrorCode.MEMBER_CONFLICT, memberName);
        }

        if(memberRepository.existsByEmail(memberEmail)) {
            throw new ConflictException(ErrorCode.MEMBER_CONFLICT, memberEmail);
        }

        Member member = Member.builder()
                .name(memberName)
                .password(passwordEncoder.encode(createMemberDto.getPassword()))
                .email(memberEmail)
                .country(createMemberDto.getCountry())
                .address(createMemberDto.getAddress())
                .role(Role.MEMBER)
                .build();

        Long memberId = memberRepository.save(member).getMemberId();

        gcsService.uploadImages(ImageType.PROFILE_IMAGE, imageFiles, String.valueOf(memberId));
    }

    @Transactional
    public void updateMember(Long memberId, UpdateMemberDto updateMemberDto, List<MultipartFile> imageFiles) throws IOException {
        Member member = verifyMember(memberId);
        String memberName = updateMemberDto.getName();
        String memberEmail = updateMemberDto.getEmail();

        if((!member.getName().equals(memberName)) && memberRepository.existsByName(memberName)) {
            throw new ConflictException(ErrorCode.MEMBER_CONFLICT, memberName);
        }

        if((!member.getEmail().equals(memberEmail)) && memberRepository.existsByEmail(memberEmail)) {
            throw new ConflictException(ErrorCode.MEMBER_CONFLICT, memberEmail);
        }

        member.update(passwordEncoder.encode(updateMemberDto.getPassword()),
                updateMemberDto.getName(),
                updateMemberDto.getEmail(),
                updateMemberDto.getCountry(),
                updateMemberDto.getAddress());

        gcsService.updateImages(ImageType.PROFILE_IMAGE, imageFiles, String.valueOf(member.getMemberId()));
    }

    public void updateMemberRole(Long memberId, Role role) {
        Member member = verifyMember(memberId);
        member.update(role);
    }

    public FindMemberDto findMember(Long memberId) {
        Member member = verifyMember(memberId);
        List<String> profileImageUrls = gcsService.findImages(ImageType.PROFILE_IMAGE, String.valueOf(memberId));

        return new FindMemberDto(member.getName(),
                member.getEmail(),
                member.getCountry(),
                member.getAddress(),
                member.getRole(),
                profileImageUrls.size() == 0 ? "" : profileImageUrls.get(0));
    }

    @Transactional
    public void deleteMember(Long memberId) {
        Member member = verifyMember(memberId);

        memberRepository.delete(member);
    }

    public Member verifyMember(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException());
    }

}