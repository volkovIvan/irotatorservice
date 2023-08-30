package com.ivanvolkov.imagerotatorservice.domain;

import com.ivanvolkov.imagerotatorservice.aws.AwsS3Service;
import com.ivanvolkov.imagerotatorservice.data.Task;
import com.ivanvolkov.imagerotatorservice.data.TaskRepository;
import com.ivanvolkov.imagerotatorservice.data.TaskState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileRotatorServiceImpl implements FileRotatorService {

    private final TaskRepository taskRepository;
    private final AwsS3Service awsS3Service;

    @Override
    public void rotateAndSaveFileFromTask(String taskId) throws IOException {
        log.info("started rotating");
        Task task = this.taskRepository.getTask(taskId).orElseThrow();
        task.setState(TaskState.IN_PROGRESS);
        task = taskRepository.save(task);
        log.info("retrieved and saved task");
        byte[] imageBytes = this.awsS3Service.downloadInitialImage(task.getFileName());
        String[] splitFileName = this.splitFileName(task.getFileName());
        byte[] rotatedImageBytes = this.rotateImage(imageBytes, splitFileName[1]);
        String rotatedFileName = this.getRotatedFileName(splitFileName);
        String rotatedFilePath = this.awsS3Service.uploadProcessedImage(rotatedImageBytes, rotatedFileName);
        log.info("rotated and uploaded image");
        task.setState(TaskState.DONE);
        task.setProcessedFilePath(rotatedFilePath);
        taskRepository.save(task);
        log.info("saved task");
    }

    private String[] splitFileName(String originalFileName) {
        return originalFileName.split("\\.(?=[^.]*$)");
    }

    private String getRotatedFileName(String[] splitFileName){
        splitFileName[0] += "rotated";
        return String.join(".", splitFileName);
    }

    private byte[] rotateImage(byte[] imageBytes, String extension) throws IOException {
        InputStream is = new ByteArrayInputStream(imageBytes);
        BufferedImage img = ImageIO.read(is);
        // Getting Dimensions of image
        int width = img.getWidth();
        int height = img.getHeight();

        // Creating a new buffered image
        BufferedImage newImage = new BufferedImage(
                img.getWidth(), img.getHeight(), img.getType());

        // creating Graphics in buffered image
        Graphics2D g2 = newImage.createGraphics();

        // Rotating image by degrees using toradians()
        // method
        // and setting new dimension t it
        g2.rotate(Math.toRadians(180), width / 2,
                height / 2);
        g2.drawImage(img, null, 0, 0);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(newImage, extension, baos);
        return baos.toByteArray();
    }
}
