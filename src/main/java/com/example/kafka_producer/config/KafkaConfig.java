package com.example.kafka_producer.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import reactor.kafka.sender.SenderOptions;

@Configuration
@RequiredArgsConstructor
public class KafkaConfig {
  private final KafkaProperties kafkaProperties;

  @Bean
  public ReactiveKafkaProducerTemplate<String, String> reactiveKafkaProducerTemplate() {
    return new ReactiveKafkaProducerTemplate<>(
        SenderOptions.create(kafkaProperties.buildProducerProperties()));
  }
}
