package com.gdscsolutionchallenge.shareBite.postTag.repository;

import com.gdscsolutionchallenge.shareBite.postTag.entity.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostTagRepository extends JpaRepository<PostTag, Long> {
    void deleteByPost_PostId(Long postId);
}
