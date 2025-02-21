package com.example.kafka_producer.service;

import com.example.kafka_producer.dto.MessageRequest;
import com.example.kafka_producer.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageProducerService {
  private final ReactiveKafkaProducerTemplate<String, String> kafkaTemplate;

  public Mono<MessageResponse> sendMessage(MessageRequest messageRequest) {
    return kafkaTemplate
        .send(messageRequest.getTopic(), messageRequest.getKey(), messageRequest.getMessage())
        .map(
            senderResult ->
                MessageResponse.builder()
                    .message(messageRequest.getMessage())
                    .partition(senderResult.recordMetadata().partition())
                    .offset(senderResult.recordMetadata().offset())
                    .status("SENT")
                    .topic(messageRequest.getTopic())
                    .build())
        .doOnSuccess(
            response ->
                log.info(
                    "Sent message: {} to partition: {}",
                    messageRequest.getMessage(),
                    response.getPartition()))
        .doOnError(error -> log.error("Error sending message: {}", error.getMessage()));
  }
}
