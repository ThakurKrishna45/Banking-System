# 🏦 Concurrency-Safe Banking System Backend

A production-grade RESTful banking backend built with **Spring Boot** that supports secure account management, concurrent fund transfers, loan processing, and transaction tracking.

Engineered with a strong focus on financial correctness, ACID compliance, and concurrency safety. Fully containerized using **Docker** and validated with **JUnit 5 & Mockito**.

---

## 🚀 Core Features

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

### 🏦 Loan Management

* Apply for loans linked to accounts
* Track loan status

---

## 🛡️ Financial Integrity & Concurrency Safety

This system mirrors real-world banking safeguards:

* **ACID-compliant transactions** using Spring `@Transactional`
* **Pessimistic locking** (`PESSIMISTIC_WRITE`) to prevent concurrent balance corruption
* **Atomic transfers** — no partial updates
* **Automatic rollback** on failure
* **BigDecimal** for precise monetary calculations

---

## 🧠 Technical Highlights

* Layered architecture (Controller → Service → Repository → Entity)
* DTO-based API design (no direct entity exposure)
* Global exception handling with custom domain exceptions
* ModelMapper for object mapping
* Clean RESTful endpoints

---

## ⚡ Performance Optimization

* Implemented Redis caching using Spring Cache (`@Cacheable`, `@CacheEvict`)
* Optimized `getAccountById` API with caching layer
* Achieved ~80% reduction in response time (30 ms → 6 ms)
* Reduced database load and improved scalability under high traffic

  ---
  
# 🐳 Dockerized Deployment

The application is fully containerized using Docker and orchestrated with Docker Compose for consistent environments and one-command startup.

## ▶️ Run Using Docker Compose (Recommended)

This will build the image from the Dockerfile and start all services defined in `compose.yaml`.

```bash
docker compose up --build
```
## ▶️ Run in Detached Mode
```bash
docker compose up -d --build
```
## ▶️ Stop and Remove Containers
```bash
docker compose down
```
Server will be available at:

👉 [http://localhost:8080](http://localhost:8080)

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
* **Database:** PostgreSQL (or any JPA‑compatible DB)
* **ORM:** Spring Data JPA / Hibernate
* **Build Tool:** Maven
* **Mapping:** ModelMapper
* **Containerization:** Docker
* **Testing:** JUnit 5, Mockito

---

## 📂 Project Structure

```
banking-system
│
├── src/main/java/com.krishna.banking
│   │
│   ├── config          # Configuration classes
│   ├── constants       # Application constants
│   ├── controller      # REST API layer
│   ├── entity          # JPA entities
│   │   └── dto         # Request & response models
│   ├── exception       # Custom exceptions & handlers
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
3. Debit sender account
4. Credit receiver account
5. Record transaction entries
6. Commit transaction atomically

---

## ▶️ Getting Started (Without Docker)

### Prerequisites

* Java 17+
* Maven
* PostgreSQL (optional)

### Run Locally

```bash
git clone https://github.com/your-username/banking-system.git
cd banking-system
mvn spring-boot:run
```

---

## 📘 API Documentation (Swagger UI)

Interactive API documentation is available after starting the application:

👉 [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

Use Swagger UI to:

* Explore all REST endpoints
* View request/response schemas
* Execute APIs directly from the browser
* Test scenarios without external tools

---

## 📌 Sample API Endpoints

### Customer

```
POST   /customers
GET    /customers/{id}
```

### Account

```
POST   /accounts
GET    /accounts/{id}
GET    /accounts/customer/{customerId}
PUT    /accounts/deactivate/{id}
```

### Fund Transfer

```
POST   /transactions/transfer
```

### Mini Statement

```
GET    /transactions/statement/{accountId}
```

### Loan

```
POST   /loans/apply
GET    /loans/account/{accountId}
```

---

## 📈 Possible Future Enhancements

* JWT authentication & role-based authorization
* Idempotent transfer APIs
* Audit logging
* Rate limiting
* Redis caching
* Event-driven notifications

---

## 👨‍💻 Author

**Krishna Pratap Singh**
Backend Developer | Java | Spring Boot | SQL | DSA
