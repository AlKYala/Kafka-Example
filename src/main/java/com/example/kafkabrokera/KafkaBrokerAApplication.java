package com.example.kafkabrokera;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class KafkaBrokerAApplication {

    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private static String bootstrapServers;

    public static void main(String[] args) {
        SpringApplication.run(KafkaBrokerAApplication.class, args);
    }
}
