package com.ivanvolkov.imagerotatorservice.data;

import java.util.Optional;

public interface TaskRepository {

    Task save(Task task);

    Optional<Task> getTask(String id);
}

