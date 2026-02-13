package com.project.event_ingestion_service.controller;

import com.project.event_ingestion_service.dto.EventRequest;
import com.project.event_ingestion_service.kafka.producer.EventProducer;
import com.project.event_ingestion_service.logging.KafkaLogPublisher;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventProducer producer;
    private final KafkaLogPublisher logPublisher;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping
    public ResponseEntity<String> publish(@Valid @RequestBody EventRequest request) {

        String traceId = MDC.get("traceId");
        logPublisher.log("INFO", "event-ingestion", traceId, "Received event request of type " + request.getEventType());

        try {
            // mock DLQ - pushes directly to DLQ
            if(request.getEventType().equals("EVENT_DLQ_TEST")){
                producer.send("user-events-dlq", request);
                return ResponseEntity.ok("Event published");
            }

            // normal flow
            producer.send("user-events", request);
            logPublisher.log("INFO", "event-ingestion", traceId, "Event published successfully to Kafka"
            );
            return ResponseEntity.ok("Event published");

        } catch (Exception ex) {
            logPublisher.log("ERROR", "event-ingestion", traceId, "Failed to publish event: " + ex.getMessage()
            );
            return ResponseEntity.internalServerError()
                    .body("Event publish failed");
        }
    }
}
