package com.ivanvolkov.imagerotatorservice.domain;

import com.ivanvolkov.imagerotatorservice.data.TaskState;
import lombok.Data;

@Data
public class TaskDto {

    private String id;
    private String fileName;

    private String originalFilePath;

    private String processedFilePath;

    private TaskState state;
}
