package com.gdscsolutionchallenge.shareBite.post;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

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

    @Column
    private String foodCategory;

}
