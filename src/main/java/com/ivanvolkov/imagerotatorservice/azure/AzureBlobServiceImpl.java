package com.ivanvolkov.imagerotatorservice.azure;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class AzureBlobServiceImpl implements AzureBlobService {

    private final BlobContainerClient blobContainerClient;

    @Override
    public String uploadProcessedImage(InputStream inputStream, String fileName) throws IOException {
        BlobClient blob = blobContainerClient.getBlobClient(fileName);
        try(inputStream) {
            blob.upload(inputStream, true);
        }
        return blob.getBlobUrl();
    }

    @Override
    public byte[] downloadInitialImage(String fileName) {
        BlobClient blob = blobContainerClient.getBlobClient(fileName);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        blob.downloadStream(outputStream);
        return outputStream.toByteArray();
    }

}
