package com.gdscsolutionchallenge.shareBite.foodCategory;

import com.gdscsolutionchallenge.shareBite.audit.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
