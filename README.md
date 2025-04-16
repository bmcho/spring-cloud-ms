# spring-cloud-ms

#실시간 채팅 상담 쇼핑몰 프로젝트 설계 정리

## ✅ 1. 프로젝트 목표

- 상품 조회 및 주문 기능이 있는 **쇼핑몰 플랫폼**
- **고객-상담원 간 실시간 채팅** 기능 포함
- 대규모 트래픽을 고려한 **MSA + Kafka + Redis + WebFlux**
- **DDD 설계 원칙**을 기반으로 서비스별 도메인 구조화

---

## 🧱 2. 전체 시스템 아키텍처

```
scss
복사편집
                            [ Client (Web/Mobile) ]
                                       │
                             [Spring Cloud Gateway]
                                       │
 ┌────────────┬────────────┬────────────┬────────────┬────────────┬────────────┐
 ▼            ▼            ▼            ▼            ▼            ▼
[Auth]     [User]      [Product]     [Order]      [Chat]     [Notification]
 (DB)       (DB)         (DB)         (DB)         (Redis)       (Redis)
                                       │             │              │
                                   Kafka Topic: "order-event", "chat-message"

```

---

## 📌 3. 서비스별 분리 기준 (Bounded Context)

| 모듈명 | 역할 | 주요 컴포넌트 |
| --- | --- | --- |
| config-server | 중앙 구성 관리 | spring-cloud-config-server, bus-amqp (RabbitMQ) |
| discovery-server | 서비스 레지스트리 | spring-cloud-starter-netflix-eureka-server |
| api-gateway | 요청 라우팅 및 필터링 | spring-cloud-starter-gateway, eureka-client, config |
| user-service | 사용자 도메인 서비스 | web, config, eureka-client, openfeign, resilience4j |
| order-service | 주문 도메인 서비스 | 위와 동일한 스택, 도메인만 다름 |
| payment-service | 결제 처리 서비스 | JPA/MyBatis 연동, 외부 PG 통신 |
| auth-service | 인증/인가 처리 | JWT, Spring Security, OAuth2 |
| zipkin-server | 트레이싱 수집 서버 | Docker 기반 실행 (OpenZipkin) |

## 🔐 주요 의존성 및 기능 구성

| 기능 | 사용 라이브러리 |
| --- | --- |
| 설정 중앙화 | `spring-cloud-config-server` |
| 서비스 등록/탐색 | `spring-cloud-starter-netflix-eureka-server/client` |
| API Gateway | `spring-cloud-starter-gateway` |
| 마이크로서비스 간 통신 | `spring-cloud-starter-openfeign` |
| 장애 대응 | `resilience4j-spring-boot3:2.1.0` |
| 분산 추적 | `micrometer-tracing-bridge-brave:1.2.3`, `zipkin-reporter-brave:2.16.4` |
| 실시간 구성 반영 | `spring-cloud-starter-bus-amqp` + `rabbitmq` |
| DB 연동 (선택) | `spring-boot-starter-data-jpa`, `mybatis-spring-boot-starter:3.0.3` |

---

## 🧩 4. 기술 구성요소

| 요소 | 기술 스택 |
| --- | --- |
| API 통합 | Spring Cloud Gateway |
| 인증 | Spring Security + JWT |
| 비동기 이벤트 | Kafka (chat, order 등) |
| 캐시/세션 | Redis |
| 실시간 통신 | WebSocket (WebFlux), 또는 SSE |
| DB 접근 | JPA + QueryDSL (Reactive는 R2DBC 선택 가능) |
| 설정 | Spring Cloud Config |
| 서비스 탐색 | Eureka |
| 배포 | Docker + Docker Compose (또는 K8s) |

---

---

## 📌 5. 다음 추천 단계

| 단계 | 설명 |
| --- | --- |
| 1단계 | 각 서비스 도메인 모델 설계 (Product, Order, Chat 등) |
| 2단계 | Gradle 멀티 프로젝트 구성 및 공통 모듈 분리 |
| 3단계 | `product-service` 같은 핵심 도메인 먼저 완성 |
| 4단계 | Redis/Kafka 통합 및 WebSocket PoC 구현 |
| 5단계 | Docker Compose로 로컬 분산환경 테스트 |

---
