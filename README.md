# 🚀 Pulse: 
## Event-Driven Analytics Platform (Java | Spring Boot | Kafka | Elasticsearch)

A **production-grade, event-driven analytics system** built using **modern Java backend technologies** and **big-tech architectural patterns**.

This project demonstrates how large-scale systems handle:
- Real-time event ingestion
- Stream processing
- Searchable analytics
- Secure microservices communication
- Observability and reliability

> Designed with **industry best practices**, **interview readiness**, and **real-world scalability** in mind.

---

## 📌 Why This Project?

Most projects stop at CRUD.

This one doesn’t.

This system mirrors how companies like **Netflix, Uber, Amazon, and Meta** design backend platforms for:
- User activity tracking
- System analytics
- Log aggregation
- Event processing at scale

If you’re evaluating this project as a recruiter or engineer — this is **not a tutorial demo**.  
It is an **architecture-first, backend-heavy system**.

---

## 🏗️ High-Level Architecture

```
Client / Services
        ↓
Authentication Service (JWT)
        ↓
Event Ingestion Service (REST → Kafka)
        ↓
Kafka Topics
        ↓
Analytics Processor Service
        ↓
Elasticsearch
        ↓
Analytics Query APIs / Dashboards
```

---

## 🧩 Microservices Overview

### 1️⃣ Authentication Service
- User signup & login  
- JWT (Access + Refresh tokens)  
- Role-based authorization  

**Tech**: Spring Security, JWT, PostgreSQL

---

### 2️⃣ Event Ingestion Service
- Accept events via REST APIs  
- Validate and enrich events  
- Publish events to Kafka  

**Highlights**: Idempotency (Redis), schema validation, secure endpoints

---

### 3️⃣ Analytics Processor Service
- Consume Kafka events  
- Transform and aggregate data  
- Persist analytics into Elasticsearch  

**Highlights**: Retry strategy, Dead Letter Topics

---

### 4️⃣ Analytics Query Service
- Query analytics data  
- Time-based aggregations  
- Filtered and paginated results  

---

### 5️⃣ Log Processing Service
- Centralized log ingestion  
- Correlation via trace IDs  
- Searchable logs  

---

## 🛠️ Technology Stack

- Java 17  
- Spring Boot 3.x  
- Apache Kafka  
- Elasticsearch 8  
- PostgreSQL  
- Redis  
- Docker & Docker Compose  

---

## 🔐 Security Design

- Stateless JWT authentication  
- Access & refresh token strategy  
- Role-based authorization  

---

## 🔁 Transaction Consistency

Implements the **Outbox Pattern** to avoid dual-write issues between DB and Kafka.

---

## 🐳 Running Locally

### Prerequisites
- Java 17  
- Docker & Docker Compose  
- Maven  

### Start Infrastructure
```bash
docker-compose up -d
```

### Run Services
```bash
mvn clean install
mvn spring-boot:run
```

---

## 🎯 What This Project Demonstrates

- Event-driven architecture  
- Kafka & Elasticsearch expertise  
- Secure microservices  
- Production-grade backend design  

---
