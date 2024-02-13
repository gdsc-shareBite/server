package com.gdscsolutionchallenge.shareBite.member.state;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    MEMBER,
    STORE_OWNER,
    ADMIN;

    public static Role findRole(String targetRole) {
        for(Role role : values()) {
            if(role.name().equals(targetRole)) return role;
        }

        return null; // todo exception 던지도록 변경하던가 방법을 구상할 것
    }
}