package com.gdscsolutionchallenge.shareBite.store;

import com.gdscsolutionchallenge.shareBite.audit.Auditable;
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

}
