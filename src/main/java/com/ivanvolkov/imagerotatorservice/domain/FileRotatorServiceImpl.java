package com.ivanvolkov.imagerotatorservice.domain;

import com.ivanvolkov.imagerotatorservice.azure.AzureBlobService;
import com.ivanvolkov.imagerotatorservice.data.Task;
import com.ivanvolkov.imagerotatorservice.data.TaskRepository;
import com.ivanvolkov.imagerotatorservice.data.TaskState;
import lombok.RequiredArgsConstructor;
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
public class FileRotatorServiceImpl implements FileRotatorService {

    private final TaskRepository taskRepository;
    private final AzureBlobService azureBlobService;

    @Override
    public void rotateAndSaveFileFromTask(TaskDto taskDto) throws IOException {
        byte[] imageBytes = this.azureBlobService.downloadInitialImage(taskDto.getFileName());
        String[] splitFileName = this.splitFileName(taskDto.getFileName());
        byte[] rotatedImageBytes = this.rotateImage(imageBytes, splitFileName[1]);
        String rotatedFileName = this.getRotatedFileName(splitFileName);
        String rotatedFilePath = this.azureBlobService.uploadProcessedImage(new ByteArrayInputStream(rotatedImageBytes), rotatedFileName);
        Task task = new Task();
        task.setId(taskDto.getId());
        task.setFileName(taskDto.getFileName());
        task.setOriginalFilePath(task.getOriginalFilePath());
        task.setProcessedFilePath(rotatedFilePath);
        task.setState(TaskState.DONE);
        taskRepository.save(task);
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
        g2.rotate(Math.toRadians(90), width / 2,
                height / 2);
        g2.drawImage(img, null, 0, 0);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(img, extension, baos);
        return baos.toByteArray();
    }
}
