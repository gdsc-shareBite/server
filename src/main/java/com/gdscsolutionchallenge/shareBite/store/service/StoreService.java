package com.gdscsolutionchallenge.shareBite.store.service;

import com.gdscsolutionchallenge.shareBite.exception.code.ErrorCode;
import com.gdscsolutionchallenge.shareBite.exception.exceptions.ResourceNotFoundException;
import com.gdscsolutionchallenge.shareBite.gcs.service.GcsService;
import com.gdscsolutionchallenge.shareBite.gcs.state.ImageType;
import com.gdscsolutionchallenge.shareBite.member.entity.Member;
import com.gdscsolutionchallenge.shareBite.member.service.MemberService;
import com.gdscsolutionchallenge.shareBite.store.dto.CreateStoreDto;
import com.gdscsolutionchallenge.shareBite.store.dto.FindStoreDto;
import com.gdscsolutionchallenge.shareBite.store.dto.UpdateStoreDto;
import com.gdscsolutionchallenge.shareBite.store.entity.Store;
import com.gdscsolutionchallenge.shareBite.store.repository.StoreRepository;
import com.gdscsolutionchallenge.shareBite.store.state.RegistrationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class StoreService {
    private final StoreRepository storeRepository;
    private final MemberService memberService;
    private final GcsService gcsService;

    @Transactional
    public void createStore(Long memberId, CreateStoreDto createStoreDto, List<MultipartFile> storeImageFiles) throws IOException {
        Member member = memberService.verifyMember(memberId);

        Store store = Store.builder()
                .name(createStoreDto.getName())
                .address(createStoreDto.getAddress())
                .build();

        store.setMember(member);

        Long storeId = storeRepository.save(store).getStoreId();

        gcsService.uploadImages(ImageType.STORE_IMAGE, storeImageFiles, String.valueOf(storeId));
    }

    @Transactional
    public void updateStore(Long memberId, UpdateStoreDto updateStoreDto, List<MultipartFile> storeImageFiles) throws IOException {
        Store store = findStoreByMemberId(memberId);

        store.update(updateStoreDto.getName(),
                updateStoreDto.getAddress(),
                updateStoreDto.getLikeCount(),
                updateStoreDto.getDislikeCount(),
                updateStoreDto.getRegistrationStatus());

        gcsService.updateImages(ImageType.STORE_IMAGE, storeImageFiles, String.valueOf(store.getStoreId()));
    }

    @Transactional
    public void updateStoreStatus(Long storeId, RegistrationStatus registrationStatus) {
        Store store = verifyStore(storeId);
        store.update(registrationStatus);
    }

    public FindStoreDto findStore(Long memberId) {
        Store store = findStoreByMemberId(memberId);

        return new FindStoreDto(store.getName(),
                store.getAddress(),
                store.getLikeCount(),
                store.getDislikeCount(),
                gcsService.findImages(ImageType.STORE_IMAGE, String.valueOf(store.getStoreId())),
                store.getRegistrationStatus());
    }

    @Transactional
    public void deleteStore(Long memberId) {
        Store store = findStoreByMemberId(memberId);

        storeRepository.delete(store);
    }

    private Store findStoreByMemberId(Long memberId) {
        Member member = memberService.verifyMember(memberId);
        Long storeId = member.getStore().getStoreId();
        return storeRepository.findById(storeId).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.NOT_FOUND_STORE));
    }

    public Store verifyStore(Long storeId) {
        return storeRepository.findById(storeId).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.NOT_FOUND_ROLE));
    }

}
