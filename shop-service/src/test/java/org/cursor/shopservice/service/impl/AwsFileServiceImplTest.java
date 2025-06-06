package org.cursor.shopservice.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.cursor.shopservice.service.impl.AwsFileServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.InputStreamResource;
import org.springframework.test.util.ReflectionTestUtils;
import org.apache.http.client.methods.HttpGet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AwsFileServiceImplTest {

    @Mock
    private AmazonS3 s3Client;

    private AwsFileServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new AwsFileServiceImpl(s3Client);
        ReflectionTestUtils.setField(service, "bucketName", "bucket");
    }

    @Test
    void upload_putsObjectAndClosesStream() throws IOException {
        InputStream is = spy(new ByteArrayInputStream("data".getBytes()));

        service.upload("key", is);

        verify(s3Client).putObject("bucket", "key", is, null);
        verify(is).close();
    }

    @Test
    void download_existingObject_returnsResource() {
        S3Object s3Object = mock(S3Object.class);
        ByteArrayInputStream bais = new ByteArrayInputStream("d".getBytes());
        when(s3Client.doesObjectExist("bucket", "key")).thenReturn(true);
        when(s3Client.getObject("bucket", "key")).thenReturn(s3Object);
        when(s3Object.getObjectContent()).thenReturn(new S3ObjectInputStream(bais, new HttpGet()));

        Optional<InputStreamResource> result = service.download("key");

        assertThat(result).isPresent();
        verify(s3Client).getObject("bucket", "key");
    }

    @Test
    void download_missingObject_returnsEmpty() {
        when(s3Client.doesObjectExist("bucket", "key")).thenReturn(false);

        Optional<InputStreamResource> result = service.download("key");

        assertThat(result).isEmpty();
    }

    @Test
    void deleteAll_delegatesToS3() {
        service.deleteAll("k1", "k2");

        ArgumentCaptor<DeleteObjectsRequest> captor = ArgumentCaptor.forClass(DeleteObjectsRequest.class);
        verify(s3Client).deleteObjects(captor.capture());
        assertThat(captor.getValue().getBucketName()).isEqualTo("bucket");
    }
}
