package com.demo.sqs.listener;

import com.demo.sqs.dto.Message;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;

@Service
@Slf4j
public class SqsMessageListenerService {

    private final AtomicBoolean isProcessingPriorityMessage = new AtomicBoolean(false);

    @SqsListener("https://sqs.ap-south-1.amazonaws.com/975050288929/priorityQueue")
    public void processPriorityMessage(String message) {
        if (isProcessingPriorityMessage.compareAndSet(false, true)) {
            {
                try {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    log.info("Processing priority message: {}", message);
                } finally {
                    isProcessingPriorityMessage.set(false);
                }
            }
        }
    }

    @SqsListener("https://sqs.ap-south-1.amazonaws.com/975050288929/mainQueue")
    public void processMainMessage(String message) {
        if (!isProcessingPriorityMessage.get()) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("Processing main queue message: {}", message);
        }else{
            log.info("Skipping main queue message because priority message is being processed.");
        }

    }

}
