package com.gdscsolutionchallenge.shareBite.gcs.service;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GcsService {
    @Autowired
    private Storage storage;
    @Value("${spring.cloud.gcp.bucket}")
    private String BUCKET_NAME;
    private final String GCS_IMAGE_PATH = "https://storage.googleapis.com/post-images/";

    public void uploadImages(MultipartFile[] imageFiles, Long postId) throws IOException {
        Integer imageIdx = 0;

        for(MultipartFile imageFile : imageFiles) {
            String imageName = imageFile.getOriginalFilename();
            String extension = imageFile.getContentType();
            String fileName = postId + "_" + imageName + "_" + imageIdx;

            try {
                BlobInfo blobInfo = storage.create(
                        BlobInfo.newBuilder(BUCKET_NAME, fileName)
                                .setContentType(extension)
                                .build(),
                        imageFile.getInputStream()
                );

            } catch (IOException e) {
                // todo: 예외 감싸기
                e.printStackTrace();
            } catch (StorageException e) {
                // todo: 예외 감싸기
                e.printStackTrace();
            }

            imageIdx++;
        }
    }

    public void updateImages(MultipartFile[] imageFiles, Long postId) throws IOException {
        deleteImages(postId);

        uploadImages(imageFiles, postId);
    }

    public List<String> findImages(Long postId) {
        List<String> imageUrls = new ArrayList<>();

        Page<Blob> blobs = storage.list(BUCKET_NAME, Storage.BlobListOption.prefix(String.valueOf(postId)));

        for (Blob blob : blobs.iterateAll()) {
            BlobId blobId = blob.getBlobId();
            String imageUrl = GCS_IMAGE_PATH + BUCKET_NAME + "/" + blobId.getName();
            imageUrls.add(imageUrl);
        }

        return imageUrls;
    }

    public void deleteImages(Long postId) {
        Page<Blob> blobs = storage.list(BUCKET_NAME, Storage.BlobListOption.prefix(String.valueOf(postId)));

        for (Blob blob : blobs.iterateAll()) {
            BlobId blobId = blob.getBlobId();
            boolean deleted = storage.delete(blobId);

            if (deleted) {
                System.out.println("Blob deleted successfully: " + blobId.getName());
            } else {
                System.out.println("Failed to delete blob: " + blobId.getName());
            }
        }
    }

}
