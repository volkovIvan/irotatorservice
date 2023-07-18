package com.ivanvolkov.imagerotatorservice.azure;

import java.io.IOException;
import java.io.InputStream;

public interface AzureBlobService {

    String uploadProcessedImage(InputStream inputStream, String fileName) throws IOException;

    byte[] downloadInitialImage(String fileName);
}
