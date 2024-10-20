package com.demo.sqs.publisher;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.GetQueueUrlResult;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.demo.sqs.dto.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class SqsMessagePublisherService {

    private final AmazonSQS amazonSQSClient;
    private final ObjectMapper objectMapper;

    public boolean publishMessage(String queueName, Message message){
        try{
            GetQueueUrlResult queueUrl = amazonSQSClient.getQueueUrl(queueName);
            SendMessageResult result = amazonSQSClient.sendMessage(queueUrl.getQueueUrl(), objectMapper.writeValueAsString(message));
            log.info(result.toString());
            return true;
        }catch(Exception e){
            log.error("Queue Exception Message : {}", e.getMessage());
            return false;
        }
    }
}