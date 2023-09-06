package com.swm.ventybackend.config;


import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Configuration {
    @Value("${AWS_S3_ACCESSKEY}")
    private String s3AccessKey;

    @Value("${AWS_S3_SECRETKEY}")
    private String s3SecretKey;

    @Value("${AWS_REGION}")
    private String region;

    @Bean
    public AmazonS3Client amazonS3Client() {
        BasicAWSCredentials s3Credentials = new BasicAWSCredentials(s3AccessKey, s3SecretKey);
        return (AmazonS3Client) AmazonS3ClientBuilder
                .standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(s3Credentials))
                .build();
    }
}
