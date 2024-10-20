package com.demo.sqs.controller;

import com.demo.sqs.dto.Message;

import com.demo.sqs.publisher.SqsMessagePublisherService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Value("${aws.mainQueueName}")
    private String mainQueueName;

    @Value("${aws.priorityQueueName}")
    private String priorityQueueName;
    private final SqsMessagePublisherService publisher;

    public MessageController(SqsMessagePublisherService publisher){
        this.publisher = publisher;
    }


    @GetMapping("/health")
    public ResponseEntity<?> healthStatus(){
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "up"));
    }

    @PostMapping("/publish/main")
    public ResponseEntity<?> publishMessageToMainQueue(@RequestBody Message message){
        boolean isPublished = publisher.publishMessage(mainQueueName, message);
        if(isPublished){
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("message", "Message published successfully", "messageId", message.getId()));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "Some exception occurred. Your message is not published."));
    }


    @PostMapping("/publish/priority")
    public ResponseEntity<?> publishMessageToPriorityQueue(@RequestBody Message message){
        boolean isPublished = publisher.publishMessage(priorityQueueName, message);
        if(isPublished){
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("message", "Message published successfully", "messageId", message.getId()));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "Some exception occurred. Your message is not published."));
    }
}