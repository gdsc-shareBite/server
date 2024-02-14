package com.gdscsolutionchallenge.shareBite.tag.service;

import com.gdscsolutionchallenge.shareBite.exception.code.ErrorCode;
import com.gdscsolutionchallenge.shareBite.exception.exceptions.ConflictException;
import com.gdscsolutionchallenge.shareBite.exception.exceptions.ResourceNotFoundException;
import com.gdscsolutionchallenge.shareBite.tag.dto.TagDto;
import com.gdscsolutionchallenge.shareBite.tag.entity.Tag;
import com.gdscsolutionchallenge.shareBite.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TagService {
    private final TagRepository tagRepository;

    @Transactional
    public void createTags(List<String> tags) {
        for(String tagName : tags) {
            if(tagRepository.existsByName(tagName)) {
                throw new ConflictException(ErrorCode.CONFLICT_TAG, tagName);
            }

            Tag tag = new Tag(tagName);

            tagRepository.save(tag);
        }
    }

    @Transactional
    public void updateTags(List<TagDto> tagDtoList) {
        for(TagDto tagDto : tagDtoList) {
            String tagName = tagDto.getName();

            if(tagRepository.existsByName(tagName)) {
                throw new ConflictException(ErrorCode.CONFLICT_TAG, tagName);
            }

            Tag tag = verifyTag(tagDto.getId());
            tag.update(tagName);
        }
    }

    public List<TagDto> findAllTags() {
        List<Tag> tags = tagRepository.findAll();

        List<TagDto> tagDtoList = new ArrayList<> ();
        for(Tag tag : tags) {
            tagDtoList.add(new TagDto(tag.getTagId(), tag.getName()));
        }

        return tagDtoList;
    }

    @Transactional
    public void deleteTags(List<Long> tagIdS) {
        for(Long tagId : tagIdS) {
            Tag tag =  verifyTag(tagId);
            tagRepository.delete(tag);
        }
    }

    public Tag verifyTag(Long tagId) {
        return tagRepository.findById(tagId).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.NOT_FOUND_TAG));
    }

}
