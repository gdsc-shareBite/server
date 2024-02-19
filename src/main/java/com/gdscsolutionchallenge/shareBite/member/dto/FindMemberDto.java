package com.gdscsolutionchallenge.shareBite.member.dto;

import com.gdscsolutionchallenge.shareBite.member.state.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FindMemberDto {
    private String name;
    private String email;
    private String country;
    private String address;
    private Role role;
    private String profileImageUrl;
}
