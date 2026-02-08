package com.project.analytics_processor_service.logging;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "app-logs")
public class LogDocument {

    @Id
    private String id;

    private String service;
    private String level;
    private String traceId;
    private String message;
    private String timestamp;
}
