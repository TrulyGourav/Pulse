package com.project.analytics_processor_service.repository;

import com.project.analytics_processor_service.document.UserEventDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UserEventRepository
        extends ElasticsearchRepository<UserEventDocument, String> {
}
