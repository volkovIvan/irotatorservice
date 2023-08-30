package com.ivanvolkov.imagerotatorservice.messaging;

import java.io.IOException;

public interface TaskListener {

    void handleTaskMessage(String message) throws IOException;
}
