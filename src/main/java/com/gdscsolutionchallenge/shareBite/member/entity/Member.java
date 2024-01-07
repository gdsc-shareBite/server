package com.gdscsolutionchallenge.shareBite.member.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name =  "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    public Member update(String name, String picture) {
        this.name = name;
        this.picture = picture;

        return this;
    }

    public String getRole() {
        return role.getRole();
    }

}
