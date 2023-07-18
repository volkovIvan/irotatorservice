package com.ivanvolkov.imagerotatorservice.data;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends CosmosRepository<Task, String> {
}

