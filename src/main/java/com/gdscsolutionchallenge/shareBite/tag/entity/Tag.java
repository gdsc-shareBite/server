package com.gdscsolutionchallenge.shareBite.tag.entity;

import com.gdscsolutionchallenge.shareBite.audit.CreationInfo;
import com.gdscsolutionchallenge.shareBite.postTag.entity.PostTag;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity(name = "TAGS")
public class Tag extends CreationInfo {
    @Column(name = "TAG_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;

    @Column
    private String name;

    @OneToMany(mappedBy = "tag", cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<PostTag> postTags = new ArrayList<>();

    public Tag(String name) {
        this.name = name;
    }
}
