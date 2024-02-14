package com.gdscsolutionchallenge.shareBite.store.entity;

import com.gdscsolutionchallenge.shareBite.audit.ModificationInfo;
import com.gdscsolutionchallenge.shareBite.blacklist.entity.Blacklist;
import com.gdscsolutionchallenge.shareBite.member.entity.Member;
import com.gdscsolutionchallenge.shareBite.post.entity.Post;
import com.gdscsolutionchallenge.shareBite.store.state.RegistrationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "STORES")
public class Store extends ModificationInfo {
    @Column(name = "STORE_ID")
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

    @Enumerated(EnumType.STRING)
    @Column
    private RegistrationStatus registrationStatus;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    /*
     * todo: cascade 추후 수정할 것
     * */
    @OneToMany(mappedBy = "store", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<Blacklist> blacklist = new ArrayList<>();

    /*
     * todo: cascade 추후 수정할 것
     * */
    @OneToMany(mappedBy = "store", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();

    public void setMember(Member member) {
        this.member = member;
        member.setStore(this);
    }

    @Builder
    public Store(String name, String address) {
        this.name = name;
        this.address = address;
        this.likeCount = 0;
        this.dislikeCount = 0;
        this.registrationStatus = RegistrationStatus.PENDING_APPROVAL;
    }

    public void update(String name, String address, Integer likeCount, Integer dislikeCount, RegistrationStatus registrationStatus) {
        this.name = name;
        this.address = address;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
        this.registrationStatus = registrationStatus;
    }

    public void update(RegistrationStatus registrationStatus) {
        this.registrationStatus = registrationStatus;
    }

}
