package com.gdscsolutionchallenge.shareBite.role;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN("ADMIN"),
    MEMBER("USER");

    private final String role;
}