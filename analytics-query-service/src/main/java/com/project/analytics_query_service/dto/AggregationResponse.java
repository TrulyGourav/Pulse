package com.project.analytics_query_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AggregationResponse {
    private String key;
    private long count;
}
