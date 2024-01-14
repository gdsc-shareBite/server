package com.gdscsolutionchallenge.shareBite.store;

import com.gdscsolutionchallenge.shareBite.audit.Auditable;
import com.gdscsolutionchallenge.shareBite.blacklist.entity.Blacklist;
import com.gdscsolutionchallenge.shareBite.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "STORES")
public class Store extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeId;

    @Column
    private String name;

    @Column
    private String address;

    @Column
    private Integer likeCount;

    @Column
    private Integer dislikeCount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "store", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<Blacklist> blacklist = new ArrayList<>();

}
