## Why do we store ROLE_XXX instead of just USER or ADMIN?
Spring Security internally expects roles to be prefixed with ROLE_, so using it explicitly avoids hidden magic and access-control bugs.

## Why do we NOT return the User entity directly from the controller?
Because entities are persistence models, not API contracts.

Returning entities directly can:
Expose sensitive fields (like password hashes)
Couple API responses tightly to database schema
Break clients when internal fields change
Cause lazy-loading / serialization issues

👉 Best practice:
Use DTOs to control what data is exposed and keep layers decoupled.

## Why do we add JwtFilter before UsernamePasswordAuthenticationFilter?
We add JwtFilter before UsernamePasswordAuthenticationFilter so JWT-based authentication is processed first, instead of Spring Security’s default username/password (form login) mechanism.

## How do refresh tokens work?
We use short-lived access tokens for authorization and long-lived refresh tokens to reissue access tokens without re-authentication. This reduces security risk while maintaining good user experience.

In production, refresh tokens should be stored securely and rotated, often backed by Redis or database for revocation.

## Why do we use eventType as Kafka message key?
The message key decides the partition in Kafka. Kafka does this internally:
partition = hash(key) % number_of_partitions

Same key → same partition
Same partition → order is guaranteed

## What if we didn’t use a key?
If key = null:

- Kafka distributes messages randomly
- No ordering guarantee
- Harder analytics

## Why do we use 'auto-offset-reset=earliest' in analytics systems?
Because it allows the consumer to replay all past events from the beginning when there is no committed offset.
“We use auto-offset-reset=earliest so analytics consumers can replay historical data when needed.”

## How do you scale Kafka consumers?

## How do you handle failures (DLQ)?

## How do you rebuild analytics?

## Why Elasticsearch over DB?

## Big-tech principle (remember this)
Kafka consumers must be 'at-least-once', but applications must be idempotent.
“We use Kafka retries with DLQ and idempotent consumers to safely handle at-least-once delivery.”

“PostgreSQL roles are created only during initial container initialization. If the container already exists, credentials must be created manually or the volume reset.”

## How do you handle Java time types in JSON?
Answer:
“We use Jackson JSR-310 module and rely on Spring Boot’s auto-configured ObjectMapper instead of creating new instances.”
We normalize event timestamps at ingestion to avoid Elasticsearch mapping conflicts

## In analytics-heavy systems:
❌ Do NOT mix repository-based reads with raw indexing
✅ Use ElasticsearchOperations for ALL reads
Interview line:
“For analytics queries we use ElasticsearchOperations instead of repositories to avoid mapping constraints.”

“Since events are indexed via Kafka consumers using raw Elasticsearch clients, we use ElasticsearchOperations for all analytics reads instead of Spring Data repositories to avoid mapping inconsistencies.”

## How production systems handle DLQ replay (3 patterns)
Pattern 1 — Idempotent main consumer (MANDATORY)
Pattern 2 — Mark DLQ events as “replayed” (MOST COMMON)
Pattern 3 — Move DLQ → Retry topic → Commit offset (BEST PRACTICE)

## How do you handle Kafka message failures?
Answer: We retry transient failures, route permanent failures to a DLQ, inspect them, and replay after fixing the data or schema.

## Why not infinite retries?
Answer: Infinite retries block partitions and stall the consumer group.

## Where duplicates can happen (be honest)
Duplicates can happen when:

Consumer processes event
Writes to Elasticsearch
Crashes before offset commit
Kafka redelivers same message

## Why EXACTLY-ONCE is HARD (and rare)
True exactly-once means: Kafka commit + DB write happen atomically

That requires:
Distributed transactions
Two-phase commit
Or Kafka transactions

These are: Complex, Slower, Hard to operate, Overkill for analytics

## Kafka’s “Exactly Once Semantics” (EOS) — reality check
Kafka does support EOS, but ONLY when:

Kafka → Kafka
Using Kafka Streams
Using transactional producers
NOT external systems like Elasticsearch

❌ Kafka cannot guarantee exactly-once into ES
❌ ES does not participate in Kafka transactions
So, "True EOS is impossible"

## What companies actually do: Big-tech rule (very important)
Exactly-once is a business requirement, not a default choice.

| Use case  | Guarantee                   |
| --------- | --------------------------- |
| Payments  | Exactly-once                |
| Inventory | Exactly-once                |
| Analytics | At-least-once + idempotency |
| Logs      | At-least-once               |
| Metrics   | At-least-once               |

This project is about analytics , So choice is correct.
Kafka guarantees delivery, not uniqueness.
Uniqueness is the application’s responsibility.


