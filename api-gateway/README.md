# API Gateway - Spring Cloud Gateway

Este proyecto implementa un API Gateway usando Spring Cloud Gateway para enrutar peticiones a los microservicios `ms-personas` y `ms-cuenta-movimiento`.

## Configuración

### Puertos
- **API Gateway**: 8080
- **Eureka Server**: 8761 (requerido)
- **ms-personas**: Se descubre automáticamente vía Eureka
- **ms-cuenta-movimiento**: Se descubre automáticamente vía Eureka

### Rutas Configuradas

| Endpoint | Microservicio de Destino | Descripción |
|----------|-------------------------|-------------|
| `/api/clientes/**` | `ms-personas` | Gestión de clientes (con stripPrefix) |
| `/api/cuentas/**` | `ms-cuenta-movimiento` | Gestión de cuentas |
| `/api/movimientos/**` | `ms-cuenta-movimiento` | Gestión de movimientos |
| `/api/reportes/**` | `ms-cuenta-movimiento` | Reportes |

### Características Implementadas

1. **Service Discovery**: Integración con Eureka Client
2. **Load Balancing**: Balanceador de carga automático con `lb://`
3. **CORS**: Configuración global para permitir peticiones cross-origin
4. **Retry**: Reintentos automáticos en caso de falla (3 intentos)
5. **Timeout**: Configuración de timeouts para conexiones
6. **Logging**: Filtro personalizado para logging de requests/responses
7. **Error Handling**: Manejo global de errores
8. **Health Checks**: Endpoints de actuator para monitoreo

## Cómo Ejecutar

### Prerrequisitos
1. Java 21
2. Maven 3.6+
3. Eureka Server ejecutándose en puerto 8761
4. Los microservicios `ms-personas` y `ms-cuenta-movimiento` registrados en Eureka

### Ejecución
```bash
# Compilar
mvn clean compile

# Ejecutar
mvn spring-boot:run

# O ejecutar el JAR
mvn clean package
java -jar target/api-gateway-0.0.1-SNAPSHOT.jar
```

## Endpoints de Monitoreo

- **Health**: `http://localhost:8080/actuator/health`
- **Routes**: `http://localhost:8080/actuator/gateway/routes`
- **Gateway Info**: `http://localhost:8080/actuator/gateway`

## Ejemplo de Uso

```bash
# Obtener clientes
curl -X GET http://localhost:8080/api/clientes

# Crear cuenta
curl -X POST http://localhost:8080/api/cuentas \
  -H "Content-Type: application/json" \
  -d '{"clienteId": 1, "saldo": 1000.0}'

# Obtener movimientos
curl -X GET http://localhost:8080/api/movimientos?cuentaId=1
```

## Configuración Adicional

El proyecto soporta tanto `application.properties` como `application.yml`. Para cambiar entre ellos, simplemente renombra o elimina uno de los archivos.

### Variables de Entorno

Puedes personalizar la configuración usando variables de entorno:

```bash
export EUREKA_URL=http://eureka-server:8761/eureka/
export GATEWAY_PORT=8080
```

## Troubleshooting

1. **Puerto 8080 ocupado**: Cambiar `server.port` en la configuración
2. **Eureka no disponible**: Verificar que el servidor Eureka esté ejecutándose
3. **Microservicios no se encuentran**: Verificar que estén registrados en Eureka
4. **CORS errors**: Revisar la configuración CORS en `application.yml`
