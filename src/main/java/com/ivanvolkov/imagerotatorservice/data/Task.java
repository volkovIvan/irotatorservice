package com.ivanvolkov.imagerotatorservice.data;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.GeneratedValue;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Container(containerName = "tasks")
@Data
public class Task {

    @PartitionKey
    @Id
    @GeneratedValue
    private String id;

    private String fileName;

    private String originalFilePath;

    private String processedFilePath;

    private TaskState state;
}
