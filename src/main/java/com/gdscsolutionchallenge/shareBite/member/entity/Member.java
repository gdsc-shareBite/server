package com.gdscsolutionchallenge.shareBite.member.entity;

import com.gdscsolutionchallenge.shareBite.audit.Auditable;
import com.gdscsolutionchallenge.shareBite.blacklist.entity.Blacklist;
import com.gdscsolutionchallenge.shareBite.role.Role;
import com.gdscsolutionchallenge.shareBite.store.Store;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "MEMBERS")
public class Member extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String profileImage;

    @Column
    private String address;

    @OneToOne(mappedBy = "member", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private Store store;

    @OneToMany(mappedBy = "member", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<Blacklist> blacklist = new ArrayList<>();

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
