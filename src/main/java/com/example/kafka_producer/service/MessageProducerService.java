package com.example.kafka_producer.service;

import com.example.kafka_producer.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MessageProducerService {
  private static final String TOPIC = "test-topic";
  private final ReactiveKafkaProducerTemplate<String, String> kafkaTemplate;

  public Mono<MessageResponse> sendMessage(String key, String message) {
    return kafkaTemplate
        .send(TOPIC, key, message)
        .map(
            senderResult ->
                MessageResponse.builder()
                    .message(message)
                    .partition(senderResult.recordMetadata().partition())
                    .offset(senderResult.recordMetadata().offset())
                    .status("SENT")
                    .build())
        .doOnSuccess(
            response ->
                System.out.println(
                    "Sent message: " + message + " to partition: " + response.getPartition()))
        .doOnError(error -> System.err.println("Error sending message: " + error.getMessage()));
  }
}
