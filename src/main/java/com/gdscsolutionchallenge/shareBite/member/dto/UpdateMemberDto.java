package com.gdscsolutionchallenge.shareBite.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMemberDto {
    private String password;
    private String name;
    private String email;
    private String country;
    private String address;

}
