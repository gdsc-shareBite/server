package com.gdscsolutionchallenge.shareBite.blacklist.entity;

import com.gdscsolutionchallenge.shareBite.blacklist.state.BlacklistCategory;
import com.gdscsolutionchallenge.shareBite.blacklist.state.BlacklistStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "BlacklistLogs")
public class BlacklistLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long blacklistLogId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="blacklist_id")
    private Blacklist blacklistId;

    @Enumerated(EnumType.STRING)
    private BlacklistStatus blacklistStatus = BlacklistStatus.ACTIVE;

    @Enumerated(EnumType.STRING)
    private BlacklistCategory blacklistCategory = BlacklistCategory.BLACK_CONSUMER;

    @Column
    private String reason;
}
