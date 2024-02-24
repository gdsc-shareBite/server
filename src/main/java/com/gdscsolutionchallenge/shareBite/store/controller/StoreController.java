package com.gdscsolutionchallenge.shareBite.store.controller;

import com.gdscsolutionchallenge.shareBite.store.dto.CreateStoreDto;
import com.gdscsolutionchallenge.shareBite.store.dto.FindStoreDto;
import com.gdscsolutionchallenge.shareBite.store.dto.UpdateStoreDto;
import com.gdscsolutionchallenge.shareBite.store.dto.UpdateStoreRegistrationStatusDto;
import com.gdscsolutionchallenge.shareBite.store.service.StoreService;
import com.gdscsolutionchallenge.shareBite.store.state.RegistrationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/stores")
public class StoreController {
    private final StoreService storeService;

    @PostMapping
    public ResponseEntity<?> createStore(@AuthenticationPrincipal UserDetails userDetails,
                                         @RequestPart(value = "createStoreDto") CreateStoreDto createStoreDto,
                                         @RequestPart(value = "imageFiles") List<MultipartFile> imageFiles) throws IOException {
        Long memberId = Long.parseLong(userDetails.getUsername());
        storeService.createStore(memberId, createStoreDto, imageFiles);

        return new ResponseEntity<> (HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateStore(@AuthenticationPrincipal UserDetails userDetails,
                                         @RequestPart(value = "updateStoreDto") UpdateStoreDto updateStoreDto,
                                         @RequestPart(value = "imageFiles") List<MultipartFile> imageFiles) throws IOException {
        Long memberId = Long.parseLong(userDetails.getUsername());
        storeService.updateStore(memberId, updateStoreDto, imageFiles);

        return new ResponseEntity<> (HttpStatus.OK);
    }

    @PatchMapping("/{storeId}/status")
    public ResponseEntity<?> updateStoreStatus(@PathVariable Long storeId,
                                               @RequestBody @Valid UpdateStoreRegistrationStatusDto updateStoreRegistrationStatusDto) {
        storeService.updateStoreStatus(storeId, updateStoreRegistrationStatusDto.getRegistrationStatus());

        return new ResponseEntity<> (HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<FindStoreDto> findStore(@AuthenticationPrincipal UserDetails userDetails) {
        Long memberId = Long.parseLong(userDetails.getUsername());

        return new ResponseEntity<> (storeService.findStore(memberId), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteStore(@AuthenticationPrincipal UserDetails userDetails) {
        Long memberId = Long.parseLong(userDetails.getUsername());
        storeService.deleteStore(memberId);

        return new ResponseEntity<> (HttpStatus.OK);
    }
}
