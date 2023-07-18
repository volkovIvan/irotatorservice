package com.ivanvolkov.imagerotatorservice.domain;

import java.io.IOException;

public interface FileRotatorService {

    void rotateAndSaveFileFromTask(TaskDto taskDto) throws IOException;
}
