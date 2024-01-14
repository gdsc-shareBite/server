package com.gdscsolutionchallenge.shareBite.blacklist.state;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BlacklistStatus {
    ACTIVE("black list activated"),
    INACTIVE("black list inactivated");

    private final String status;
}