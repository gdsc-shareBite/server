package com.gdscsolutionchallenge.shareBite.post.entity;

import com.gdscsolutionchallenge.shareBite.audit.Auditable;
import com.gdscsolutionchallenge.shareBite.member.entity.Member;
import com.gdscsolutionchallenge.shareBite.order.entity.Order;
import com.gdscsolutionchallenge.shareBite.post.state.PostStatus;
import com.gdscsolutionchallenge.shareBite.postTag.entity.PostTag;
import com.gdscsolutionchallenge.shareBite.store.Store;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "POSTS")
public class Post extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private LocalDateTime foodPurchaseDate;

    @Column
    private LocalDateTime foodCookingDate;

    @Column
    private LocalDateTime foodExpirationData;

    @Column
    private LocalDateTime foodBestBeforeDate;

    @Column
    private Integer foodQuantity;

    @Enumerated(EnumType.STRING)
    private PostStatus postStatus = PostStatus.SHARING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STORE_ID")
    private Store store;

    /*
     * todo: cascade 추후 수정할 것
     * */
    @OneToMany(mappedBy = "post", cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

    /*
     * todo: cascade 추후 수정할 것
     * */
    @OneToMany(mappedBy = "post", cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<PostTag> postTags = new ArrayList<>();

    public void setStore(Store store) {
        this.store = store;
        store.getPosts().add(this);
    }

}
