package com.gdscsolutionchallenge.shareBite.postFoodCategory;

import com.gdscsolutionchallenge.shareBite.foodCategory.FoodCategory;
import com.gdscsolutionchallenge.shareBite.post.Post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PostFoodCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postFoodCategoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FOOD_CATEGORY_ID")
    private FoodCategory foodCategory;
}
