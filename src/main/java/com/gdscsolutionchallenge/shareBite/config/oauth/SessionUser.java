package com.gdscsolutionchallenge.shareBite.config.oauth;

import com.gdscsolutionchallenge.shareBite.member.entity.Member;

public class SessionUser {
    private String name;
    private String email;
    private String picture;

    public SessionUser(Member member) {
        this.name = member.getName();
        this.email = member.getEmail();
        this.picture = member.getPicture();
    }
}
