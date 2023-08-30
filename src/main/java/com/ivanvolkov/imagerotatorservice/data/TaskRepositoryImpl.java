package com.ivanvolkov.imagerotatorservice.data;

import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.Key;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepository {

    private final DynamoDbTemplate dynamoDbTemplate;

    @Override
    public Task save(Task task) {
        if (task.getId() == null) {
            task.setId(UUID.randomUUID().toString());
        }
        return this.dynamoDbTemplate.save(task);
    }

    @Override
    public Optional<Task> getTask(String id) {
        Key key = Key.builder().partitionValue(id).build();
        return Optional.ofNullable(this.dynamoDbTemplate.load(key, Task.class));
    }
}
