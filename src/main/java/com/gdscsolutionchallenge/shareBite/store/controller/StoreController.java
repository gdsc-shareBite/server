package com.gdscsolutionchallenge.shareBite.store.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/stores")
public class StoreController {
    @PostMapping
    public ResponseEntity<String> postStore() {
        return new ResponseEntity<> ("created store", HttpStatus.OK);
    }
}
