package com.gdscsolutionchallenge.shareBite.member.dto;

import com.gdscsolutionchallenge.shareBite.member.state.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMemberRoleDto {
    @NotNull
    private Role role;
}
