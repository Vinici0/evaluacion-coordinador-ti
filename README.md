# Microservicios Bancarios - Spring Boot Reactive

Sistema de microservicios para gestión bancaria implementado con **programación reactiva** y **arquitectura hexagonal**, utilizando Spring WebFlux y functional endpoints.

## 🏗️ Arquitectura

### Microservicios
- **ms-eurekaserver** (8761) - Service Discovery
- **api-gateway** (8086) - Gateway reactivo con Spring Cloud Gateway
- **ms-cliente-persona** (8081) - Gestión de clientes con PostgreSQL + Kafka Consumer
- **ms-cuenta-movimiento** (8082) - Manejo de cuentas y transacciones con PostgreSQL + Kafka Producer
- **ms-rastreador** (8084) - Trazabilidad de operaciones

### Infraestructura
- **Kafka** (9092) - Message Broker para eventos entre microservicios
- **Zookeeper** (2181) - Coordinación de Kafka
- **PostgreSQL** - Bases de datos separadas por microservicio

### Características Técnicas
- **Programación Reactiva**: Spring WebFlux con Mono/Flux
- **Event-Driven Architecture**: Kafka para comunicación asíncrona entre servicios
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
# 1. Infraestructura base
docker-compose up -d zookeeper kafka postgres-cliente postgres-cuenta

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
| API Gateway | http://localhost:8086 | Punto de entrada principal |
| MS Cliente | http://localhost:8081 | Gestión de clientes |
| MS Cuenta | http://localhost:8082 | Cuentas y movimientos |
| MS Rastreador | http://localhost:8084 | Trazabilidad |
| Kafka | localhost:9092 | Message Broker |
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
- **Message Broker**: Apache Kafka 7.4.0
- **Base de Datos**: PostgreSQL 15
- **Service Discovery**: Eureka Server
- **Gateway**: Spring Cloud Gateway
- **Containerización**: Docker & Docker Compose
- **Reactive Programming**: Project Reactor (Mono/Flux)
- **Event-Driven**: Kafka Streams para comunicación asíncrona

## 📁 Estructura del Proyecto

```
├── api-gateway/          # Gateway reactivo
├── ms-eurekaserser/      # Service discovery
├── ms-cliente-persona/   # Microservicio de clientes + Kafka Consumer
├── ms-cuenta-movimiento/ # Microservicio de cuentas + Kafka Producer
├── ms-rastreador/        # Microservicio de trazabilidad
├── docker-compose.yml    # Orquestación de contenedores
└── .env                  # Variables de entorno
```

## 📨 **Flujo de Eventos con Kafka**

### **Event-Driven Flow implementado:**

1. **MS-Cuenta-Movimiento** crea un movimiento → Publica evento a Kafka (Topic: `movement-events`)
2. **MS-Cliente-Persona** escucha evento → Simula envío de email al cliente
3. **Log detallado** se registra mostrando:
   - ✅ Tópico recibido con partition/offset
   - 📧 Simulación de envío de email con detalles del movimiento
   - ✉️ Confirmación de procesamiento

### **Componentes implementados:**
- **Producer**: `MovementEventService` en ms-cuenta-movimiento
- **Consumer**: `NotificationService` en ms-cliente-persona  
- **DTO**: `MovementEventDto` para estructura del evento
- **Config**: Configuración Kafka para Producer/Consumer

Cada microservicio implementa **functional endpoints** para mayor rendimiento y control granular sobre el flujo reactivo de datos.
