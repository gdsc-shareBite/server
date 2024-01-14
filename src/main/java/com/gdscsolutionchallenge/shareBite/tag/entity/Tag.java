package com.gdscsolutionchallenge.shareBite.tag.entity;

import com.gdscsolutionchallenge.shareBite.audit.Auditable;
import com.gdscsolutionchallenge.shareBite.postTag.entity.PostTag;
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
@Entity(name = "TAGS")
public class Tag extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long foodCategoryId;

    @Column
    String name;

    @OneToMany(mappedBy = "foodCategory", cascade={CascadeType.ALL})
    private List<PostTag> postFoodCategories = new ArrayList<>();
}
