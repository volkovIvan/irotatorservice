package com.ivanvolkov.imagerotatorservice;

import com.azure.spring.data.cosmos.repository.config.EnableCosmosRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableCosmosRepositories
public class ImageRotatorServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImageRotatorServiceApplication.class, args);
    }

}
