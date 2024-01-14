package com.gdscsolutionchallenge.shareBite.blacklist.entity;

import com.gdscsolutionchallenge.shareBite.audit.Auditable;
import com.gdscsolutionchallenge.shareBite.member.entity.Member;
import com.gdscsolutionchallenge.shareBite.store.Store;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Blacklist extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long blacklistId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="store_id")
    private Store storeId;

}
