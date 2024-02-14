package com.gdscsolutionchallenge.shareBite.tag.controller;

import com.gdscsolutionchallenge.shareBite.tag.dto.CreateTagDto;
import com.gdscsolutionchallenge.shareBite.tag.dto.DeleteTagDto;
import com.gdscsolutionchallenge.shareBite.tag.dto.TagsDto;
import com.gdscsolutionchallenge.shareBite.tag.dto.UpdateTagsDto;
import com.gdscsolutionchallenge.shareBite.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/tags")
public class TagController {
    private final TagService tagService;
    @PostMapping
    public ResponseEntity<?> createTags(@RequestBody CreateTagDto createTagDto) {
        tagService.createTags(createTagDto.getTags());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateTags(@RequestBody UpdateTagsDto updateTagsDto) {
        tagService.updateTags(updateTagsDto.getTagDtoList());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<TagsDto> findAllTags() {

        return new ResponseEntity<> (new TagsDto(tagService.findAllTags()), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTags(@RequestBody DeleteTagDto deleteTagDto) {
        tagService.deleteTags(deleteTagDto.getTagIds());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
