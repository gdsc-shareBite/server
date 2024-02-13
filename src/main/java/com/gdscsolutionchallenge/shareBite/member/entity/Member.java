package com.gdscsolutionchallenge.shareBite.member.entity;

import com.gdscsolutionchallenge.shareBite.audit.ModificationInfo;
import com.gdscsolutionchallenge.shareBite.blacklist.entity.Blacklist;
import com.gdscsolutionchallenge.shareBite.order.entity.Order;
import com.gdscsolutionchallenge.shareBite.member.state.Role;
import com.gdscsolutionchallenge.shareBite.store.entity.Store;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity(name = "MEMBERS")
@NoArgsConstructor
public class Member extends ModificationInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long memberId;

    @Setter
    @Column
    private String password;

    @Setter
    @Column
    private String name;

    @Setter
    @Column
    private String email;

    @Setter
    @Column
    private String country;

    @Setter
    @Column
    private String address;

    /*
    * todo: cascade 추후 수정할 것
    * */
    @OneToOne(mappedBy = "member", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private Store store;

    /*
     * todo: cascade 추후 수정할 것
     * */
    @OneToMany(mappedBy = "member", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<Blacklist> blacklist = new ArrayList<>();

    /*
     * todo: cascade 추후 수정할 것
     * */
    @OneToMany(mappedBy = "member", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

    @Setter
    @Enumerated(EnumType.STRING)
    @Column
    private Role role;

    public void setStore(Store store) {
        this.store = store;
    }

    @Builder
    public Member(String password, String name, String email, String country, String address, Role role) {
        this.password = password;
        this.name = name;
        this.email = email;
        this.country = country;
        this.address = address;
        this.role = role;
    }

    public void update(String password, String name, String email, String country, String address) {
        setPassword(password);
        setName(name);
        setEmail(email);
        setCountry(country);
        setAddress(address);
    }

    public void update(Role role) {
        setRole(role);
    }
}
