package com.minio.config;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "minio")
public class MinioConfig {
    private String accessKey;

    private String secretKey;

    private String url;

    private String bucketName;

    private String publicUrl;

    /*@Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(url)
                .credentials(accessKey, secretKey)
                .build();
    }*/

    @Bean
    public IMinioClient getMinioClient() {
        MinioClient minioClient = MinioClient.builder().endpoint(url).credentials(accessKey, secretKey).build();
        IMinioClient client = new IMinioClient(minioClient);
        client.setPublicBaseUrl(publicUrl);
        return client;
    }
}
