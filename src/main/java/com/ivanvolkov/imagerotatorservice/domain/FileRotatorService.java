package com.ivanvolkov.imagerotatorservice.domain;

import com.ivanvolkov.imagerotatorservice.messaging.TaskMessage;

import java.io.IOException;

public interface FileRotatorService {

    void rotateAndSaveFileFromTask(String taskId) throws IOException;
}
