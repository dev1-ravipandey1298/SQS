package com.demo.sqs;

import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

//	@SqsListener("https://sqs.ap-south-1.amazonaws.com/975050288929/priorityQueue")
//	public void listen(String message) {
//		System.out.println(message);
//	}

}
