package com.gdscsolutionchallenge.shareBite.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OrderController {
    @PostMapping
    public ResponseEntity<?> createOrder() {


        return new ResponseEntity<> (HttpStatus.CREATED);
    }
}
