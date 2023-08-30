package com.ivanvolkov.imagerotatorservice.aws;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class AwsS3ServiceImpl implements AwsS3Service {

    @Value("${aws.bucket-name}")
    private String bucketName;

    private final S3Client amazonS3Client;

    @Override
    public String uploadProcessedImage(byte [] bytes, String fileName) {

        PutObjectRequest requestPut = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();
        RequestBody requestBody = RequestBody.fromBytes(bytes);
        amazonS3Client.putObject(requestPut,requestBody);
        GetUrlRequest requestGet = GetUrlRequest.builder().bucket(bucketName ).key(fileName).build();
        return amazonS3Client.utilities().getUrl(requestGet).toExternalForm();
    }

    @Override
    public byte[] downloadInitialImage(String fileName) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(bucketName).key(fileName).build();
        return amazonS3Client.getObjectAsBytes(getObjectRequest).asByteArray();
    }

}
