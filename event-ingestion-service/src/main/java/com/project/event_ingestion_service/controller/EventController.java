package com.project.event_ingestion_service.controller;

import com.project.event_ingestion_service.dto.EventRequest;
import com.project.event_ingestion_service.kafka.producer.EventProducer;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventProducer producer;

    @PostMapping
    public ResponseEntity<String> publish(@Valid @RequestBody EventRequest request) {
        producer.send("user-events", request);
        return ResponseEntity.ok("Event published");
    }
}
