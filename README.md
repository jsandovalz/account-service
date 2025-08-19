# 🧾 Account Service – Microservicio de Gestión de Cuentas y Transacciones

Este microservicio se encarga de la administración de cuentas bancarias, transacciones y generación de reportes. 
Está construido con **Java 17**, **Spring Boot 3**, arquitectura limpia, y comunicación por eventos con **RabbitMQ**. 
Incluye documentación Swagger, pruebas unitarias y despliegue en contenedores Docker.

---

## ⚙️ Tecnologías utilizadas

- Java 17 + Spring Boot 3
- JPA (Hibernate)
- MySQL
- RabbitMQ
- Docker & Docker Compose
- Swagger (Springdoc OpenAPI)

---

## 🚀 Requisitos previos

- Docker instalado (`docker -v`)
- Docker Compose instalado (`docker compose version`)
- Puerto `8082` libre para el servicio
- Puerto `3307` libre para MySQL
---

## 🐳 Despliegue con Docker Compose

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/jsandovalz/account-service.git
   cd account-service
   ```
2. **Levantar la base de datos MySQL**
```bash
docker-compose up -d account-db 
```
3. Crear el package
```bash
  mvn clean install package 
```
4. Levantar los contenedores
```bash
docker compose up --build
```
5. Verificar estado
- API disponible en: http://localhost:8082/api/accounts
- Documentación Swagger: http://localhost:8082/swagger-ui/index.html

6. Popular la DB
```bash
docker exec -i account-db mysql -uuser -puserpass accountdb < src/main/resources/data.sql
```
---

## 📖 Documentación con Swagger
Este proyecto incluye documentación interactiva con Swagger UI para explorar y probar los endpoints.- URL: http://localhost:8082/swagger-ui/index.html
- Generado automáticamente con Springdoc OpenAPI
- Permite probar endpoints directamente desde el navegador
---

## 📬 Endpoints principales- GET /api/accounts – Listar cuentas
- GET /api/accounts – Lista todas las cuentas
- GET /api/accounts/{accountNumber} – Lista una simple cuenta
- POST /api/accounts – Crear cuenta
- PUT /api/accounts/{id} – Actualizar cuenta
- DELETE /api/accounts/{id} – Eliminar cuenta
- POST /api/transactions – Registrar transacción
- GET /api/reports – Generar reporte de movimientos
---

## 🧪 Pruebas unitarias
Ejecutar con Maven:
```bash
mvn test
```
Incluye:
- Pruebas de unidad para AccountService, TransactionService, ReportService

---
### 🔄 Integración por eventos
Este servicio consume eventos ClientCreatedEvent desde RabbitMQ 
para asociar clientes a cuentas. El consumidor ClientEventConsumer escucha y procesa los eventos emitidos por client-service.
---

### 🛠️ Buenas prácticas aplicadas
- Clean Code y Clean Architecture
- Patrón Repository
- DTOs separados de entidades
- Manejo global de excepciones
- Pruebas automatizadas
- Documentación Swagger
- Despliegue Dockerizado con healthchecks
- Comunicación asíncrona por eventos

