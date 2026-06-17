# 🏦 Concurrency-Safe Banking System Backend

* A production-grade Microservices Backend built with **Spring Boot**, engineered for high-integrity financial operations.
* The system manages secure account lifecycles, high-concurrency transfers, and loan processing with a strict focus on ACID compliance and financial correctness.

---

## Core Features

### 👤 Customer Management

* Create and manage customers
* Retrieve customer details
* Support multiple accounts per customer

### 💳 Account Management

* Create savings/current accounts
* Activate or deactivate accounts
* Fetch accounts by customer

### 💸 Secure Fund Transfer

* Transfer funds between accounts
* Atomic debit/credit operations
* Prevent race conditions and double spending
* Persist transactions for auditability

### 📜 Mini Statement

* Retrieve recent transactions (mini statement)
* Debit/Credit tracking with timestamps

---

## 🛡️ Financial Integrity & Concurrency Safety

This system mirrors real-world banking safeguards:

* **ACID-compliant transactions** using Spring `@Transactional`
* **Pessimistic locking** (`PESSIMISTIC_WRITE`) to prevent concurrent balance corruption
* **Atomic transfers** — no partial updates
* **Automatic rollback** on failure
* **BigDecimal** for precise monetary calculations
* **Redis-backed idempotency keys** to prevent duplicate deposits, withdrawals, and transfers caused by retries or network failures
* **Duplicate request detection** using atomic Redis operations
* **Safe retry handling** for financial transactions

---

## 🔁 Idempotent Transaction Processing

To ensure financial correctness under network failures and client retries, all transaction APIs support idempotency keys.

### Supported Operations

* Deposit
* Withdraw
* Transfer

### How It Works

1. Client sends an `Idempotency-Key` header with each transaction request
2. Redis stores and tracks processed requests
3. Duplicate requests with the same key are detected and blocked
4. Financial operations are executed only once regardless of how many times the request is retried

### Benefits

* Prevents duplicate financial operations
* Protects against accidental double-clicks
* Handles client-side retries safely
* Improves reliability in distributed environments
* Eliminates duplicate deposits, withdrawals, and transfers

### Example

```http
POST /transaction/transfer

Idempotency-Key: txn-12345
```

Reusing the same key for the same operation will not execute the transaction again.

---

## 🧠 Technical Highlights

* Layered architecture (Controller → Service → Repository → Entity)
* DTO-based API design (no direct entity exposure)
* Global exception handling with custom domain exceptions
* Decoupled Microservices using **Kafka** (Producer (Banking System) → Kafka Broker → Consumer (Notification Service))
* Clean RESTful endpoints
* Redis-backed idempotency layer for duplicate transaction prevention
* Concurrency-safe transaction processing
* Retry-safe API design

---

## ⚡ Performance Optimization

* Implemented Redis caching using Spring Cache (`@Cacheable`, `@CacheEvict`)
* Optimized `getAccountById` API with caching layer
* Achieved ~80% reduction in response time (30 ms → 6 ms)
* Reduced database load and improved scalability under high traffic

---

# 🐳 Dockerized Deployment

The application is fully containerized using Docker and orchestrated with Docker Compose for consistent environments and one-command startup.

## How to Run (With Docker)

* Pull the current repository and also pull notification-service repository [https://github.com/ThakurKrishna45/Notification-service]
* Run the command given below (How to Start Container) in banking-system repo first and then run the same command in Notification-service repo.

## How to Start Container

```bash
docker compose up --build
```

## ▶️ Stop and Remove Containers

```bash
docker compose down
```

Server will be available at:

👉 http://localhost:8080

---

## 🧪 Testing

Unit tests are implemented using:

* **JUnit 5** — Testing framework
* **Mockito** — Mocking dependencies

### Test Coverage Includes

* Transaction Controller
* Transaction Service Layer
* Core business logic validation

### Run Tests

```bash
mvn test
```

---

## 🏗️ Tech Stack

* **Backend:** Spring Boot
* **Language:** Java
* **Database:** PostgreSQL (or any JPA-compatible DB)
* **ORM:** Spring Data JPA / Hibernate
* **Build Tool:** Maven
* **Mapping:** ModelMapper
* **Containerization:** Docker
* **Testing:** JUnit 5, Mockito
* **Cache:** Redis
* **Messaging:** Apache Kafka

---

## 📂 Project Structure

```text
banking-system
│
├── src/main/java/com.krishna.banking
│   │
│   ├── config          # Configuration classes
│   ├── constants       # Application constants
│   ├── controller      # REST API layer
│   ├── entity          # JPA entities
│   │   └── dto         # Request & response models
│   ├── event           # DTO for events
│   ├── exception       # Custom exceptions & handlers
│   ├── kafka           # Producer classes
│   ├── repository      # Data access layer
│   ├── service         # Service interfaces
│   │   └── impl        # Business logic implementations
│   │
│   └── BankingSystemApplication.java
│
├── src/test            # Unit tests (JUnit + Mockito)
└── Dockerfile
```

---

## 🔄 Fund Transfer Workflow

1. Lock sender and receiver accounts using pessimistic locking
2. Validate account status and sufficient balance
3. Check idempotency key to prevent duplicate processing
4. Debit sender account
5. Credit receiver account
6. Record transaction entries
7. Commit transaction atomically

---

## 📘 API Documentation (Swagger UI)

Interactive API documentation is available after starting the application:

👉 http://localhost:8080/swagger-ui/index.html

Use Swagger UI to:

* Explore all REST endpoints
* View request/response schemas
* Execute APIs directly from the browser
* Test scenarios without external tools

---

## 📌 Sample API Endpoints

### Customer

```text
POST   /customers
GET    /customers/{id}
```

### Account

```text
POST   /accounts
GET    /accounts/{id}
GET    /accounts/customer/{customerId}
PUT    /accounts/deactivate/{id}
```

### Fund Transfer

```text
POST   /transactions/transfer
POST   /transactions/withdraw
POST   /transactions/deposit
```

### Mini Statement

```text
GET    /transactions/statement/{accountId}
```

### Loan

```text
POST   /loans/apply
GET    /loans/account/{accountId}
```

---

## 👨‍💻 Author

**Krishna Pratap Singh**
Backend Developer | Java | Spring Boot | SQL | DSA
