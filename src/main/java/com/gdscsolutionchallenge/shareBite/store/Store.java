package com.gdscsolutionchallenge.shareBite.store;

import com.gdscsolutionchallenge.shareBite.audit.ModificationInfo;
import com.gdscsolutionchallenge.shareBite.blacklist.entity.Blacklist;
import com.gdscsolutionchallenge.shareBite.member.entity.Member;
import com.gdscsolutionchallenge.shareBite.post.entity.Post;
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
public class Store extends ModificationInfo {
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

}
