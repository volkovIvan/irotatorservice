package com.ivanvolkov.imagerotatorservice.web;

import com.ivanvolkov.imagerotatorservice.domain.FileRotatorService;
import com.ivanvolkov.imagerotatorservice.domain.TaskDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rotate")
public class RotatorController {

    private final FileRotatorService fileRotatorService;

    @PostMapping(consumes = "application/json")
    public ResponseEntity handleFileUpload(@RequestBody TaskDto taskDto) throws IOException {
        this.fileRotatorService.rotateAndSaveFileFromTask(taskDto);
        return ResponseEntity.ok().build();
    }
}
