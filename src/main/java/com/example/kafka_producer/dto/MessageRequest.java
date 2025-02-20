package com.example.kafka_producer.dto;

import lombok.Data;

@Data
public class MessageRequest {
  private String message;
  private String key;
}
