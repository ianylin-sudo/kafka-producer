package com.example.kafka_producer.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageResponse {
  private String message;
  private int partition;
  private long offset;
  private String status;
}
