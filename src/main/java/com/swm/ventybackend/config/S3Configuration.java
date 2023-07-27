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
//    @Value("${cloud.aws.credentials.access-key}")
    private String s3AccessKey = "AKIARTTSXPOB3F6OQPOD";

//    @Value("${cloud.aws.credentials.secret-key}")
    private String s3SecretKey = "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";

//    @Value("${cloud.aws.region.static}")
    private String region = "ap-northeast-2";

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
