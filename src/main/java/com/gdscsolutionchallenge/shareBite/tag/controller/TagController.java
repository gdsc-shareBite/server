package com.gdscsolutionchallenge.shareBite.tag.controller;

import com.gdscsolutionchallenge.shareBite.tag.dto.CreateTagDto;
import com.gdscsolutionchallenge.shareBite.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/tags")
public class TagController {
    private final TagService tagService;
    @PostMapping
    public ResponseEntity<?> createTag(@RequestBody CreateTagDto createTagDto) {
        tagService.createTag(createTagDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
