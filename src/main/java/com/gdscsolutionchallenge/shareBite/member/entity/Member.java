package com.gdscsolutionchallenge.shareBite.member.entity;

import com.gdscsolutionchallenge.shareBite.audit.Auditable;
import com.gdscsolutionchallenge.shareBite.role.Role;
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
public class Member extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name =  "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column
    private String profileImage;

    @Column
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    public Member update(String name, String profileImage) {
        this.name = name;
        this.profileImage = profileImage;

        return this;
    }

    public String getRole() {
        return role.getRole();
    }

}
