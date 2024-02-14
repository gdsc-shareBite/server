package com.gdscsolutionchallenge.shareBite.post.entity;

import com.gdscsolutionchallenge.shareBite.audit.ModificationInfo;
import com.gdscsolutionchallenge.shareBite.order.entity.Order;
import com.gdscsolutionchallenge.shareBite.post.state.PostStatus;
import com.gdscsolutionchallenge.shareBite.postTag.entity.PostTag;
import com.gdscsolutionchallenge.shareBite.store.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
    private Long postId;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private Integer foodQuantity;

    @Column
    private LocalDateTime foodExpirationDate;

    @Column
    private LocalDateTime foodManufacturingDate;

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

    @OneToMany(mappedBy = "post", cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<PostTag> postTags = new ArrayList<>();

    public void setStore(Store store) {
        this.store = store;
        store.getPosts().add(this);
    }

    @Builder
    public Post(String title, String description, Integer foodQuantity, LocalDateTime foodExpirationDate, LocalDateTime foodManufacturingDate) {
        this.title = title;
        this.description = description;
        this.foodQuantity = foodQuantity;
        this.foodExpirationDate = foodExpirationDate;
        this.foodManufacturingDate = foodManufacturingDate;
        this.postStatus = PostStatus.SHARING;
    }

    public void update(String title, String description, Integer foodQuantity, LocalDateTime foodExpirationDate, LocalDateTime foodManufacturingDate, PostStatus postStatus) {
        this.title = title;
        this.description = description;
        this.foodQuantity = foodQuantity;
        this.foodExpirationDate = foodExpirationDate;
        this.foodManufacturingDate = foodManufacturingDate;
        this.postStatus = postStatus;
    }

}
