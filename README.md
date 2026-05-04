# Remitly Stock Market

Simplified stock market service implemented with Spring Boot and PostgreSQL.

## Requirements

- Docker with Docker Compose
- For local non-Docker development: Java 17+ and Maven

## Run

Windows PowerShell:

```powershell
.\run.ps1 -Port 8080
```

Linux/macOS:

```sh
sh run.sh 8080
```

The service is available at `http://localhost:8080`. Replace `8080` with any free port.

The Docker setup starts:

- PostgreSQL for shared durable state
- two application instances
- HAProxy load balancer exposed on the requested localhost port

`POST /chaos` terminates the application instance that handles the request. The other instance remains available behind HAProxy, and Docker restarts the terminated container.

## API

### Set bank state

```http
POST /stocks
Content-Type: application/json

{
  "stocks": [
    {"name": "stock1", "quantity": 99},
    {"name": "stock2", "quantity": 1}
  ]
}
```

Returns `200` on success.

### Get bank state

```http
GET /stocks
```

Response:

```json
{
  "stocks": [
    {"name": "stock1", "quantity": 99},
    {"name": "stock2", "quantity": 1}
  ]
}
```

### Buy or sell one stock

```http
POST /wallets/{wallet_id}/stocks/{stock_name}
Content-Type: application/json

{"type": "buy"}
```

or:

```json
{"type": "sell"}
```

Rules:

- creates the wallet if it does not exist
- returns `404` when the stock does not exist
- returns `400` when buying and the bank has no stock
- returns `400` when selling and the wallet has no stock
- logs only successful wallet operations

### Get wallet

```http
GET /wallets/{wallet_id}
```

Response:

```json
{
  "id": "12qdsdadsa",
  "stocks": [
    {"name": "stock1", "quantity": 99},
    {"name": "stock2", "quantity": 1}
  ]
}
```

### Get wallet stock quantity

```http
GET /wallets/{wallet_id}/stocks/{stock_name}
```

Response body is a single number, for example:

```text
99
```

### Get audit log

```http
GET /log
```

Response:

```json
{
  "log": [
    {"type": "buy", "wallet_id": "23qdsadsa", "stock_name": "stock1"},
    {"type": "sell", "wallet_id": "12qdsdadsa", "stock_name": "stock1"}
  ]
}
```

### Kill current instance

```http
POST /chaos
```

Returns `200`, then terminates the instance that handled the request.

## Development

Run tests:

```powershell
.\mvnw.cmd test
```

or:

```sh
./mvnw test
```

Database schema is managed by Flyway migrations in `src/main/resources/db/migration`.
