# Wallet Service

Микросервис для управления балансом кошельков с поддержкой конкурентных операций.  
**Основные функции**:
- Пополнение баланса (DEPOSIT)
- Списание средств (WITHDRAW)
- Получение текущего баланса

---

## 🛠 Технологии
- **Java 17**
- **Spring Boot 3** (Web, Data JPA, Retry)
- **PostgreSQL** (с шардированием и репликацией в прод-среде)
- **Docker** (контейнеризация)
- **Liquibase** (миграции БД)
- **Hibernate** (оптимистическая блокировка)

---

## 🚀 Запуск приложения

### Предварительные требования
- Установите:
    - Java 17 ([Oracle JDK](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html))
    - Maven ([инструкция](https://maven.apache.org/install.html))
    - Docker ([инструкция](https://docs.docker.com/engine/install/))

### 1. Сборка проекта
```bash
mvn clean package -DskipTests 
```

### 2. Запуск в Docker
```bash
docker compose up --build
```

**Порты**:
- Приложение: `http://localhost:8080`
- PostgreSQL: `5432`
- 
## Получение баланса
### Запрос
```bash
curl http://localhost:8080/api/v1/wallets/550e8400-e29b-41d4-a716-446655440000
```
### Ответ
```json
1500
```

## Обновление баланса

### Запрос
```bash
curl -X POST http://localhost:8080/api/v1/wallet \
  -H "Content-Type: application/json" \
  -d '{
    "walletId": "550e8400-e29b-41d4-a716-446655440000",
    "amount": 500,
    "operationType": "DEPOSIT"
  }'
```

### Параметры запроса
- **walletId** (UUID) — идентификатор кошелька.
- **amount** (long) — сумма операции (должна быть ≥ 0).
- **operationType** (String) — тип операции: `DEPOSIT` (пополнение) или `WITHDRAW` (списание).

### Ответ
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "balance": 1000,
  "version": 2
}
```

### Описание полей ответа
- **id** (UUID) — идентификатор кошелька.
- **balance** (long) — новый баланс после операции.
- **version** (long) — текущая версия записи (для оптимистической блокировки).