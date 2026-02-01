package com.project.analytics_processor_service.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.Instant;
import java.util.Map;

@Document(indexName = "user-events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEventDocument {

    @Id
    private String eventId;

    private String eventType;
    private String userId;
    private Instant timestamp;
    private String source;
    private Map<String, Object> payload;
}
