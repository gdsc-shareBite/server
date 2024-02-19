package com.gdscsolutionchallenge.shareBite.config.gcs;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;

@Configuration
public class GoogleCloudStorageConfig {
    @Autowired
    private ApplicationContext applicationContext;
    @Value("${spring.cloud.gcp.storage.credentials.location}")
    private String keyFilePath;
    @Value("${spring.cloud.gcp.project-id}")
    private String projectId;

    @Bean
    public Storage storage() throws IOException {

        Resource resource = applicationContext.getResource(keyFilePath);
        GoogleCredentials credentials = GoogleCredentials.fromStream(resource.getInputStream());
        return StorageOptions.newBuilder()
                .setProjectId(projectId)
                .setCredentials(credentials)
                .build()
                .getService();
    }
}
