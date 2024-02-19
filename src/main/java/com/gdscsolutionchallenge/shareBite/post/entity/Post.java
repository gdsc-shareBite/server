package com.gdscsolutionchallenge.shareBite.post.entity;

import com.gdscsolutionchallenge.shareBite.audit.ModificationInfo;
import com.gdscsolutionchallenge.shareBite.order.entity.Order;
import com.gdscsolutionchallenge.shareBite.post.state.PostStatus;
import com.gdscsolutionchallenge.shareBite.postTag.entity.PostTag;
import com.gdscsolutionchallenge.shareBite.store.entity.Store;
import lombok.*;

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

    @Setter
    @Column
    private String title;

    @Setter
    @Column
    private String description;

    @Setter
    @Column
    private Integer foodQuantity;

    @Setter
    @Column
    private LocalDateTime foodExpirationDate;

    @Setter
    @Column
    private LocalDateTime foodManufacturingDate;

    @Setter
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
        setTitle(title);
        setDescription(description);
        setFoodQuantity(foodQuantity);
        setFoodExpirationDate(foodExpirationDate);
        setFoodManufacturingDate(foodManufacturingDate);
        setPostStatus(postStatus);
    }

}
