package com.project.analytics_query_service.service;

import com.project.analytics_query_service.document.UserEventDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsQueryService {

    private final ElasticsearchOperations operations;

    /* ---------------------------------
       Get ALL events
     --------------------------------- */
    public List<UserEventDocument> getAllEvents() {

        NativeQuery query = new NativeQueryBuilder()
                .withQuery(q -> q.matchAll(m -> m))
                .build();

        SearchHits<UserEventDocument> hits =
                operations.search(query, UserEventDocument.class);

        return hits.getSearchHits()
                .stream()
                .map(hit -> hit.getContent())
                .collect(Collectors.toList());
    }

    /* ---------------------------------
       Get events by eventType
     --------------------------------- */
    public List<UserEventDocument> getEventsByType(String eventType) {

        NativeQuery query = new NativeQueryBuilder()
                .withQuery(q -> q.term(t -> t
                        .field("eventType")
                        .value(eventType)))
                .build();

        SearchHits<UserEventDocument> hits =
                operations.search(query, UserEventDocument.class);

        return hits.getSearchHits()
                .stream()
                .map(hit -> hit.getContent())
                .collect(Collectors.toList());
    }

    /* ---------------------------------
       Get events by userId
     --------------------------------- */
    public List<UserEventDocument> getEventsByUser(String userId) {

        NativeQuery query = new NativeQueryBuilder()
                .withQuery(q -> q.term(t -> t
                        .field("userId")
                        .value(userId)))
                .build();

        SearchHits<UserEventDocument> hits =
                operations.search(query, UserEventDocument.class);

        return hits.getSearchHits()
                .stream()
                .map(hit -> hit.getContent())
                .collect(Collectors.toList());
    }
}
