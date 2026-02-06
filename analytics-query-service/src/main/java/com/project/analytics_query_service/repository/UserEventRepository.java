package com.project.analytics_query_service.repository;

import com.project.analytics_query_service.document.UserEventDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface UserEventRepository
        extends ElasticsearchRepository<UserEventDocument, String> {

    List<UserEventDocument> findByEventType(String eventType);
    List<UserEventDocument> findByUserId(String userId);
}
