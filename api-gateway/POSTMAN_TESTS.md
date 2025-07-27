# Guía de Pruebas en Postman para API Gateway

## 🚀 Prerrequisitos

Antes de probar, asegúrate de que están corriendo:
1. **Eureka Server** en puerto 8761
2. **ms-personas** (registrado en Eureka)
3. **ms-cuenta-movimiento** (registrado en Eureka)
4. **API Gateway** en puerto 8080

Para iniciar el API Gateway:
```bash
./run.sh
```

## 📋 URLs de Prueba en Postman

### 1. Verificar Estado del Gateway

#### Health Check
- **URL**: `http://localhost:8080/actuator/health`
- **Método**: GET
- **Descripción**: Verifica que el gateway esté funcionando

#### Ver Rutas Configuradas
- **URL**: `http://localhost:8080/actuator/gateway/routes`
- **Método**: GET
- **Descripción**: Muestra todas las rutas configuradas

#### Estado del Gateway
- **URL**: `http://localhost:8080/actuator/gateway`
- **Método**: GET
- **Descripción**: Información detallada del gateway

### 2. Pruebas del Microservicio ms-personas

#### Obtener Todos los Clientes
- **URL**: `http://localhost:8080/api/clientes`
- **Método**: GET
- **Headers**: 
  - `Content-Type: application/json`
- **Descripción**: Lista todos los clientes (se enruta a ms-personas)

#### Obtener Cliente por ID
- **URL**: `http://localhost:8080/api/clientes/{id}`
- **Método**: GET
- **Ejemplo**: `http://localhost:8080/api/clientes/1`

#### Crear Cliente
- **URL**: `http://localhost:8080/api/clientes`
- **Método**: POST
- **Headers**: 
  - `Content-Type: application/json`
- **Body** (raw JSON):
```json
{
  "nombre": "Juan Pérez",
  "identificacion": "1234567890",
  "direccion": "Calle 123",
  "telefono": "555-0123",
  "contrasena": "password123",
  "estado": true
}
```

#### Actualizar Cliente
- **URL**: `http://localhost:8080/api/clientes/{id}`
- **Método**: PUT
- **Headers**: 
  - `Content-Type: application/json`
- **Body** (raw JSON):
```json
{
  "nombre": "Juan Pérez Actualizado",
  "identificacion": "1234567890",
  "direccion": "Calle 456",
  "telefono": "555-0456",
  "contrasena": "newpassword123",
  "estado": true
}
```

#### Eliminar Cliente
- **URL**: `http://localhost:8080/api/clientes/{id}`
- **Método**: DELETE

### 3. Pruebas del Microservicio ms-cuenta-movimiento (Cuentas)

#### Obtener Todas las Cuentas
- **URL**: `http://localhost:8080/api/cuentas`
- **Método**: GET

#### Obtener Cuenta por ID
- **URL**: `http://localhost:8080/api/cuentas/{id}`
- **Método**: GET

#### Crear Cuenta
- **URL**: `http://localhost:8080/api/cuentas`
- **Método**: POST
- **Headers**: 
  - `Content-Type: application/json`
- **Body** (raw JSON):
```json
{
  "numeroCuenta": "123456789",
  "tipoCuenta": "AHORROS",
  "saldoInicial": 1000.0,
  "estado": true,
  "clienteId": 1
}
```

#### Actualizar Cuenta
- **URL**: `http://localhost:8080/api/cuentas/{id}`
- **Método**: PUT
- **Body** (raw JSON):
```json
{
  "numeroCuenta": "123456789",
  "tipoCuenta": "CORRIENTE",
  "saldoInicial": 1500.0,
  "estado": true,
  "clienteId": 1
}
```

### 4. Pruebas del Microservicio ms-cuenta-movimiento (Movimientos)

#### Obtener Todos los Movimientos
- **URL**: `http://localhost:8080/api/movimientos`
- **Método**: GET

#### Obtener Movimientos por Cuenta
- **URL**: `http://localhost:8080/api/movimientos?cuentaId={id}`
- **Método**: GET
- **Ejemplo**: `http://localhost:8080/api/movimientos?cuentaId=1`

#### Crear Movimiento
- **URL**: `http://localhost:8080/api/movimientos`
- **Método**: POST
- **Headers**: 
  - `Content-Type: application/json`
- **Body** (raw JSON):
```json
{
  "fecha": "2025-07-26T10:00:00",
  "tipoMovimiento": "DEPOSITO",
  "valor": 500.0,
  "saldo": 1500.0,
  "cuentaId": 1
}
```

### 5. Pruebas de Reportes

#### Obtener Reportes por Cliente y Fechas
- **URL**: `http://localhost:8080/api/reportes/cliente/{clienteId}`
- **Método**: GET
- **Query Parameters**:
  - `fechaInicio`: 2025-07-01
  - `fechaFin`: 2025-07-31
- **Ejemplo**: `http://localhost:8080/api/reportes/cliente/1?fechaInicio=2025-07-01&fechaFin=2025-07-31`

## 🔧 Headers Importantes

En todas las peticiones, Postman debería mostrar estos headers de respuesta agregados por el Gateway:
- `X-Response-Gateway: api-gateway`

## 🐛 Troubleshooting

### Error 404 - Service Not Found
- Verifica que el microservicio esté registrado en Eureka
- Revisa: `http://localhost:8761` para ver servicios registrados

### Error 500 - Gateway Timeout
- El microservicio puede estar caído
- Verifica logs del gateway: mira la consola donde ejecutaste `./run.sh`

### Error CORS
- Ya está configurado en el gateway, pero si hay problemas, revisa `application.properties`

### Service Discovery No Funciona
- Verifica que Eureka esté corriendo en puerto 8761
- Los microservicios deben estar registrados con los nombres:
  - `ms-personas`
  - `ms-cuenta-movimiento`

## 📊 Logs Útiles

El gateway está configurado con logging DEBUG, así que verás en la consola:
- Requests entrantes
- Rutas utilizadas
- Respuestas de microservicios
- Errores de conexión

## 🚀 Comandos Rápidos

```bash
# Iniciar gateway
./run.sh

# Ver servicios en Eureka
curl http://localhost:8761/eureka/apps

# Health check rápido
curl http://localhost:8080/actuator/health

# Ver rutas del gateway
curl http://localhost:8080/actuator/gateway/routes
```
