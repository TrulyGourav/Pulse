package com.project.analytics_processor_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic userEventsDlq() {
        return new NewTopic("user-events-dlq", 1, (short) 1);
    }
}
