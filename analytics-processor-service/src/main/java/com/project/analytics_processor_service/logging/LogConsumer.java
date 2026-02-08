package com.project.analytics_processor_service.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class LogConsumer {

    private final ElasticsearchOperations operations;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(
            topics = "app-logs",
            groupId = "log-processor-group"
    )
    public void consume(String message) throws Exception {

        Map<String, Object> log =
                objectMapper.readValue(message, Map.class);

        LogDocument document = new LogDocument(
                UUID.randomUUID().toString(),
                (String) log.get("service"),
                (String) log.get("level"),
                (String) log.get("traceId"),
                (String) log.get("message"),
                (String) log.get("timestamp")
        );

        operations.save(document);
    }
}
