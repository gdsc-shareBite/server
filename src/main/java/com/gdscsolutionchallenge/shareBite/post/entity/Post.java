package com.gdscsolutionchallenge.shareBite.post.entity;

import com.gdscsolutionchallenge.shareBite.audit.Auditable;
import com.gdscsolutionchallenge.shareBite.postTag.entity.PostTag;
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

    @OneToMany(mappedBy = "post", cascade={CascadeType.ALL})
    private List<PostTag> postFoodCategories = new ArrayList<>();

}
