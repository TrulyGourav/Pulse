package com.project.analytics_query_service.service;

import com.project.analytics_query_service.dto.AggregationResponse;
import com.project.analytics_query_service.dto.DateAggregationResponse;
import com.project.analytics_query_service.document.UserEventDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsBucket;
import co.elastic.clients.elasticsearch._types.aggregations.DateHistogramBucket;
import co.elastic.clients.elasticsearch._types.aggregations.CalendarInterval;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AnalyticsAggregationService {

    private final ElasticsearchOperations operations;

    /* -----------------------------
       1️⃣ Count by event type
     ----------------------------- */
    public List<AggregationResponse> countByEventType() {

        Aggregation agg = Aggregation.of(a ->
                a.terms(t -> t.field("eventType"))
        );

        NativeQuery query = new NativeQueryBuilder()
                .withAggregation("by_event_type", agg)
                .build();

        SearchHits<UserEventDocument> hits =
                operations.search(query, UserEventDocument.class);

        Map<String, Aggregate> aggs =
                (Map<String, Aggregate>) hits.getAggregations().aggregations();

        List<StringTermsBucket> buckets =
                aggs.get("by_event_type")
                        .sterms()
                        .buckets()
                        .array();

        List<AggregationResponse> result = new ArrayList<>();
        for (StringTermsBucket bucket : buckets) {
            result.add(
                    new AggregationResponse(
                            bucket.key().stringValue(),
                            bucket.docCount()
                    )
            );
        }

        return result;
    }

    /* -----------------------------
       2️⃣ Events per day
     ----------------------------- */
    public List<DateAggregationResponse> eventsPerDay() {

        Aggregation agg = Aggregation.of(a ->
                a.dateHistogram(d -> d
                        .field("timestamp")
                        .calendarInterval(CalendarInterval.Day)
                )
        );

        NativeQuery query = new NativeQueryBuilder()
                .withAggregation("events_per_day", agg)
                .build();

        SearchHits<UserEventDocument> hits =
                operations.search(query, UserEventDocument.class);

        Map<String, Aggregate> aggs =
                (Map<String, Aggregate>) hits.getAggregations().aggregations();

        List<DateHistogramBucket> buckets =
                aggs.get("events_per_day")
                        .dateHistogram()
                        .buckets()
                        .array();

        List<DateAggregationResponse> result = new ArrayList<>();
        for (DateHistogramBucket bucket : buckets) {
            result.add(
                    new DateAggregationResponse(
                            bucket.keyAsString(),
                            bucket.docCount()
                    )
            );
        }

        return result;
    }

    /* -----------------------------
       3️⃣ Top active users
     ----------------------------- */
    public List<AggregationResponse> topUsers(int size) {

        Aggregation agg = Aggregation.of(a ->
                a.terms(t -> t
                        .field("userId")
                        .size(size)
                )
        );

        NativeQuery query = new NativeQueryBuilder()
                .withAggregation("top_users", agg)
                .build();

        SearchHits<UserEventDocument> hits =
                operations.search(query, UserEventDocument.class);

        Map<String, Aggregate> aggs =
                (Map<String, Aggregate>) hits.getAggregations().aggregations();

        List<StringTermsBucket> buckets =
                aggs.get("top_users")
                        .sterms()
                        .buckets()
                        .array();

        List<AggregationResponse> result = new ArrayList<>();
        for (StringTermsBucket bucket : buckets) {
            result.add(
                    new AggregationResponse(
                            bucket.key().stringValue(),
                            bucket.docCount()
                    )
            );
        }

        return result;
    }
}
