package com.gdscsolutionchallenge.shareBite.post;

import com.gdscsolutionchallenge.shareBite.postFoodCategory.PostFoodCategory;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Post {
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
    private List<PostFoodCategory> postFoodCategories = new ArrayList<>();

}
