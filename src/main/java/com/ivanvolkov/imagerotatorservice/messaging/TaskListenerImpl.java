package com.ivanvolkov.imagerotatorservice.messaging;

import com.ivanvolkov.imagerotatorservice.domain.FileRotatorService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class TaskListenerImpl implements TaskListener {

    private final FileRotatorService fileRotatorService;

    @Override
    @SqsListener("${aws.task-queue-sqs}")
    public void handleTaskMessage(String taskId) throws IOException {
        log.info("received message for task " + taskId);
        this.fileRotatorService.rotateAndSaveFileFromTask(taskId);
    }
}
