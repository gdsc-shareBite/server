package com.gdscsolutionchallenge.shareBite.member.state;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.gdscsolutionchallenge.shareBite.exception.code.ErrorCode;
import com.gdscsolutionchallenge.shareBite.exception.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    MEMBER,
    STORE_OWNER,
    ADMIN;

    @JsonCreator
    public static Role findRole(String targetRole) {
        for(Role role : values()) {
            if(role.name().equals(targetRole)) return role;
        }

        throw new ResourceNotFoundException(ErrorCode.NOT_FOUND_ROLE);
    }
}