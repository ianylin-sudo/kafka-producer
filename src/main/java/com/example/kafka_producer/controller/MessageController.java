package com.example.kafka_producer.controller;

import com.example.kafka_producer.dto.MessageRequest;
import com.example.kafka_producer.dto.MessageResponse;
import com.example.kafka_producer.service.MessageProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {
  private final MessageProducerService producerService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<MessageResponse> sendMessage(@RequestBody MessageRequest request) {
    return producerService.sendMessage(request);
  }
}
