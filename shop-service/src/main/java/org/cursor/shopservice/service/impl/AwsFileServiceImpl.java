package org.cursor.shopservice.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import lombok.RequiredArgsConstructor;
import org.cursor.shopservice.service.AwsFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AwsFileServiceImpl implements AwsFileService {

    private final AmazonS3 s3Client;

    @Value("${aws.s3.bucket:bucket}")
    private String bucketName;

    @Override
    public void upload(String key, InputStream inputStream) throws IOException {
        s3Client.putObject(bucketName, key, inputStream, null);
        inputStream.close();
    }

    @Override
    public Optional<InputStreamResource> download(String key) {
        return s3Client.doesObjectExist(bucketName, key)
                ? Optional.of(new InputStreamResource(s3Client.getObject(bucketName, key).getObjectContent()))
                : Optional.empty();
    }

    @Override
    public void deleteAll(String... keys) {
        s3Client.deleteObjects(new DeleteObjectsRequest(bucketName).withKeys(keys));
    }
}
