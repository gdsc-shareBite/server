package com.gdscsolutionchallenge.shareBite.blacklist.state;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BlacklistCategory {
    NO_SHOW("No show, didn't show up"),
    BLACK_CONSUMER("Disruptive behavior or inconvenience caused by the member during sharing activity");

    private final String category;
}
