package com.gdscsolutionchallenge.shareBite.blacklist.entity;

import com.gdscsolutionchallenge.shareBite.audit.Auditable;
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
public class Blacklist extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long blacklistId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="store_id")
    private Store store;

    @Enumerated(EnumType.STRING)
    private BlacklistStatus blacklistStatus = BlacklistStatus.ACTIVE;

    @Enumerated(EnumType.STRING)
    private BlacklistCategory blacklistCategory = BlacklistCategory.BLACK_CONSUMER;

    @Column
    private String reason;

    public void setMember(Member member) {
        if(this.member != null) this.member.getBlacklist().remove(this);
        this.member = member;
        member.getBlacklist().add(this);
    }

    public void setStore(Store store) {
        if(this.store != null) this.store.getBlacklist().remove(this);
        this.store = store;
        store.getBlacklist().add(this);
    }

}
