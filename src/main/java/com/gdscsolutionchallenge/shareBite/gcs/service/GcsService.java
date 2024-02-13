package com.gdscsolutionchallenge.shareBite.gcs.service;

import com.gdscsolutionchallenge.shareBite.exception.code.ErrorCode;
import com.gdscsolutionchallenge.shareBite.exception.exceptions.FileOperationException;
import com.gdscsolutionchallenge.shareBite.exception.exceptions.UnsupportedMediaTypeException;
import com.gdscsolutionchallenge.shareBite.gcs.state.MediaType;
import com.gdscsolutionchallenge.shareBite.gcs.state.ImageType;
import com.google.api.gax.paging.Page;
import com.google.cloud.storage.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GcsService {
    @Autowired
    private Storage storage;
//    @Value("${spring.cloud.gcp.bucket}") todo: vm에서 정상적으로 값을 주입해주고 있지 못함
    private String BUCKET_NAME = "share-bite-image-buket";
    private final String GCS_IMAGE_PATH = "https://storage.googleapis.com/";
    private final String GCS_PROFILE_IMAGE_FOLDER = "profile-images/";
    private final String GCS_POST_IMAGE_FOLDER = "post-images/";
    private final String GCS_STORE_IMAGE_FOLDER = "store-images/";

    public void uploadImages(ImageType imageType, List<MultipartFile> imageFiles, String imageId) throws IOException {
        Integer imageIdx = 0;
        String folderName = getFolderName(imageType);

        for(MultipartFile imageFile : imageFiles) {
            String mediaType = imageFile.getContentType();

            if( !(mediaType.equals(MediaType.JPEG.getMineType()) || mediaType.equals(MediaType.PNG.getMineType())) ) {
                throw new UnsupportedMediaTypeException(ErrorCode.UNSUPPORTED_MEDIA_TYPE, mediaType);
            }

            String imageName = imageFile.getOriginalFilename();
            String fileName = folderName + imageId + "_" + imageIdx + "_" + imageName;

            try {
                BlobInfo blobInfo = storage.create(
                        BlobInfo.newBuilder(BUCKET_NAME, fileName)
                                .setContentType(mediaType)
                                .build(),
                        imageFile.getInputStream()
                );
            } catch (IOException | StorageException e) {
                throw new FileOperationException(ErrorCode.FILE_OPERATION_FAILED, e);
            }

            imageIdx++;
        }
    }

    public void updateImages(ImageType imageType, List<MultipartFile> imageFiles, String imageId) throws IOException {
        deleteImages(imageType, imageId);

        uploadImages(imageType, imageFiles, imageId);
    }

    public List<String> findImages(ImageType imageType, String imageId) {
        String folderName = getFolderName(imageType);

        List<String> imageUrls = new ArrayList<>();

        try {
            Page<Blob> blobs = storage.list(BUCKET_NAME, Storage.BlobListOption.prefix(folderName + imageId + "_"));

            for (Blob blob : blobs.iterateAll()) {
                BlobId blobId = blob.getBlobId();
                String imageUrl = GCS_IMAGE_PATH + BUCKET_NAME + "/" + blobId.getName();
                imageUrls.add(imageUrl);
            }
        } catch (StorageException e) {
            throw new FileOperationException(ErrorCode.FILE_OPERATION_FAILED, e);
        }

        return imageUrls;
    }

    public void deleteImages(ImageType imageType, String imageId) {
        String folderName = getFolderName(imageType);

        try {
            Page<Blob> blobs = storage.list(BUCKET_NAME, Storage.BlobListOption.prefix(folderName + "_" + imageId + "_"));

            for(Blob blob : blobs.iterateAll()) {
                BlobId blobId = blob.getBlobId();
                boolean deleted = storage.delete(blobId);

                if (deleted) {
                    System.out.println("Blob deleted successfully: " + blobId.getName());
                } else {
                    System.out.println("Failed to delete blob: " + blobId.getName());
                }
            }
        } catch (StorageException e) {
            throw new FileOperationException(ErrorCode.FILE_OPERATION_FAILED, e);
        }
    }

    private String getFolderName(ImageType imageType) {
        switch (imageType) {
            case POST_IMAGE:
                return GCS_POST_IMAGE_FOLDER;
            case PROFILE_IMAGE:
                return GCS_PROFILE_IMAGE_FOLDER;
            case STORE_IMAGE:
                return GCS_STORE_IMAGE_FOLDER;
        }
        // enum으로 입력이 관리되고 있기 때문에 별도의 예외처리 불필요
        return null;
    }
}
