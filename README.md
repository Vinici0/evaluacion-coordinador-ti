# Microservicios Bancarios - Spring Boot Reactive

Sistema de microservicios para gestión bancaria implementado con **programación reactiva** y **arquitectura hexagonal**, utilizando Spring WebFlux y functional endpoints.

## 🏗️ Arquitectura

### Microservicios
- **ms-eurekaserver** (8761) - Service Discovery
- **api-gateway** (8085) - Gateway reactivo con Spring Cloud Gateway
- **ms-cliente-persona** (8081) - Gestión de clientes con PostgreSQL
- **ms-cuenta-movimiento** (8082) - Manejo de cuentas y transacciones con PostgreSQL
- **ms-rastreador** (8084) - Trazabilidad de operaciones

### Características Técnicas
- **Programación Reactiva**: Spring WebFlux con Mono/Flux
- **Functional Endpoints**: RouterFunction para manejo de rutas
- **Base de Datos**: PostgreSQL separada por microservicio  
- **Service Discovery**: Eureka Server
- **Gateway Reactivo**: Spring Cloud Gateway con filtros reactivos
- **Arquitectura Hexagonal**: Separación clara entre dominio, aplicación e infraestructura

## 🚀 Ejecución con Docker

### Levantar todo el sistema
```bash
docker-compose up --build -d
```

### Ejecución secuencial (recomendado)
```bash
# 1. Bases de datos
docker-compose up -d postgres-cliente postgres-cuenta

# 2. Eureka Server (esperar 60s)
docker-compose up -d ms-eurekaserver

# 3. Microservicios core
docker-compose up -d ms-rastreador ms-cliente-persona

# 4. MS Cuenta (esperar 30s)
docker-compose up -d ms-cuenta-movimiento

# 5. API Gateway
docker-compose up -d api-gateway
```

## 🌐 URLs de Acceso

| Servicio | URL | Descripción |
|----------|-----|-------------|
| Eureka Dashboard | http://localhost:8761 | Registro de servicios |
| API Gateway | http://localhost:8085 | Punto de entrada principal |
| MS Cliente | http://localhost:8081 | Gestión de clientes |
| MS Cuenta | http://localhost:8082 | Cuentas y movimientos |
| MS Rastreador | http://localhost:8084 | Trazabilidad |
| BD Cliente | localhost:5433 | PostgreSQL clientes |
| BD Cuenta | localhost:5434 | PostgreSQL cuentas |

## 📊 Monitoreo

```bash
# Estado de servicios
docker-compose ps

# Logs en tiempo real
docker-compose logs -f [servicio]

# Detener todo
docker-compose down -v
```

## 🔧 Stack Tecnológico

- **Backend**: Spring Boot 3.x, Spring WebFlux
- **Base de Datos**: PostgreSQL 15
- **Service Discovery**: Eureka Server
- **Gateway**: Spring Cloud Gateway
- **Containerización**: Docker & Docker Compose
- **Reactive Programming**: Project Reactor (Mono/Flux)

## 📁 Estructura del Proyecto

```
├── api-gateway/          # Gateway reactivo
├── ms-eurekaserser/      # Service discovery
├── ms-cliente-persona/   # Microservicio de clientes
├── ms-cuenta-movimiento/ # Microservicio de cuentas
├── ms-rastreador/        # Microservicio de trazabilidad
├── docker-compose.yml    # Orquestación de contenedores
└── .env                  # Variables de entorno
```

Cada microservicio implementa **functional endpoints** para mayor rendimiento y control granular sobre el flujo reactivo de datos.
