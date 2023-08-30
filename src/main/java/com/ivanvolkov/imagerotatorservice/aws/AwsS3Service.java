package com.ivanvolkov.imagerotatorservice.aws;

import java.io.IOException;
import java.io.InputStream;

public interface AwsS3Service {

    String uploadProcessedImage(byte [] bytes, String fileName);

    byte[] downloadInitialImage(String fileName);
}
