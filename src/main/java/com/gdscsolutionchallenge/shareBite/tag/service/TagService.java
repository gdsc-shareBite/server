package com.gdscsolutionchallenge.shareBite.tag.service;

import com.gdscsolutionchallenge.shareBite.tag.dto.CreateTagDto;
import com.gdscsolutionchallenge.shareBite.tag.entity.Tag;
import com.gdscsolutionchallenge.shareBite.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TagService {
    private final TagRepository tagRepository;

    public void createTag(CreateTagDto createTagDto) {
        String tagName = createTagDto.getName();

        if(tagRepository.existsByName(tagName)) {
            // todo: tag 중복 등록 예외
        }

        Tag tag = new Tag(tagName);

        tagRepository.save(tag);
    }

    public Tag verifyTag(Optional<Tag> optionalTag) {
        return optionalTag.orElseThrow(() -> new RuntimeException());
    }
}
