package com.gdscsolutionchallenge.shareBite.postTag.entity;

import com.gdscsolutionchallenge.shareBite.tag.entity.Tag;
import com.gdscsolutionchallenge.shareBite.post.entity.Post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "POST_TAGS")
public class PostTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postFoodCategoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FOOD_CATEGORY_ID")
    private Tag tag;

    public void setPost(Post post) {
        this.post = post;
        post.getPostTags().add(this);
    }

    public void setTag(Tag tag) {
        this.tag = tag;
        tag.getPostTags().add(this);
    }
}
