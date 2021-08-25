package org.cursor.shopservice.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsConfig {

    @Value("${aws.s3.bucket:bucket}")
    private String bucketName;

    @Value("${aws.s3.host:http://localhost:4566}")
    private String host;

    @Bean
    public AmazonS3 amazonS3() {
        AmazonS3 localstackClient = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(host, "us-west-1"))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("accesskey", "secretkey")))
                .withPathStyleAccessEnabled(true)
                .build();
        if (!localstackClient.doesBucketExistV2(bucketName)) {
            localstackClient.createBucket(bucketName);
        }
        return localstackClient;
    }

}
