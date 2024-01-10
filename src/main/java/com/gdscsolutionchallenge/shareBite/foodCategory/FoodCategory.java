package com.gdscsolutionchallenge.shareBite.foodCategory;

import com.gdscsolutionchallenge.shareBite.audit.Auditable;
import com.gdscsolutionchallenge.shareBite.postFoodCategory.PostFoodCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FoodCategory extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long foodCategoryId;

    @Column
    String name;

    @OneToMany(mappedBy = "foodCategory", cascade={CascadeType.ALL})
    private List<PostFoodCategory> postFoodCategories = new ArrayList<>();
}
