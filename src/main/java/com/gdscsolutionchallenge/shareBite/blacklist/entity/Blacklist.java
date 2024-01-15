package com.gdscsolutionchallenge.shareBite.blacklist.entity;

import com.gdscsolutionchallenge.shareBite.audit.CreationInfo;
import com.gdscsolutionchallenge.shareBite.blacklist.state.BlacklistCategory;
import com.gdscsolutionchallenge.shareBite.blacklist.state.BlacklistStatus;
import com.gdscsolutionchallenge.shareBite.member.entity.Member;
import com.gdscsolutionchallenge.shareBite.store.Store;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "BLACKLIST")
public class Blacklist extends CreationInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long blacklistId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="STORE_ID")
    private Store store;

    @Enumerated(EnumType.STRING)
    private BlacklistStatus blacklistStatus = BlacklistStatus.ACTIVE;

    @Enumerated(EnumType.STRING)
    private BlacklistCategory blacklistCategory = BlacklistCategory.BLACK_CONSUMER;

    @Column
    private String reason;

    public void setMember(Member member) {
        this.member = member;
        member.getBlacklist().add(this);
    }

    public void setStore(Store store) {
        this.store = store;
        store.getBlacklist().add(this);
    }

}
