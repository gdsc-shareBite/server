package com.gdscsolutionchallenge.shareBite.post.entity;

import com.gdscsolutionchallenge.shareBite.audit.ModificationInfo;
import com.gdscsolutionchallenge.shareBite.order.entity.Order;
import com.gdscsolutionchallenge.shareBite.post.state.PostStatus;
import com.gdscsolutionchallenge.shareBite.postTag.entity.PostTag;
import com.gdscsolutionchallenge.shareBite.store.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "POSTS")
public class Post extends ModificationInfo {
    @Column(name = "POST_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
