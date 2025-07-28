#!/bin/bash

echo "🚀 Prueba de Integración - Sistema de Notificaciones por Kafka"
echo "=============================================================="

# Función para hacer requests HTTP
make_request() {
    local url=$1
    local method=$2
    local data=$3
    local description=$4
    
    echo ""
    echo "📋 $description"
    echo "   URL: $url"
    
    if [ "$method" = "POST" ]; then
        curl -X POST "$url" \
             -H "Content-Type: application/json" \
             -d "$data" \
             --max-time 10 \
             --silent \
             --show-error \
             --write-out "\n   Status: %{http_code}\n" || echo "   ❌ Error en la solicitud"
    else
        curl -X GET "$url" \
             -H "Accept: application/json" \
             --max-time 10 \
             --silent \
             --show-error \
             --write-out "\n   Status: %{http_code}\n" || echo "   ❌ Error en la solicitud"
    fi
}

# Esperar a que los servicios estén listos
echo "⏳ Esperando a que los servicios estén disponibles..."
sleep 30

echo ""
echo "🔍 Verificando estado de los servicios..."

# Health checks
make_request "http://localhost:8761/actuator/health" "GET" "" "Estado del Eureka Server"
make_request "http://localhost:8080/actuator/health" "GET" "" "Estado del API Gateway"

echo ""
echo "👤 Consultando clientes existentes..."
make_request "http://localhost:8080/clientes" "GET" "" "Lista de clientes"

echo ""
echo "🏦 Consultando cuentas existentes..."
make_request "http://localhost:8080/cuentas" "GET" "" "Lista de cuentas"

echo ""
echo "💸 Creando movimiento (esto debería disparar notificación por Kafka)..."

# Crear un movimiento
movement_data='{
    "accountNumber": "478758", 
    "movementType": "DEPOSITO", 
    "amount": 200.0
}'

make_request "http://localhost:8080/movimientos" "POST" "$movement_data" "Crear movimiento - Depósito de $200"

echo ""
echo "💳 Creando otro movimiento..."

# Crear otro movimiento
movement_data2='{
    "accountNumber": "225487", 
    "movementType": "RETIRO", 
    "amount": -150.0
}'

make_request "http://localhost:8080/movimientos" "POST" "$movement_data2" "Crear movimiento - Retiro de $150"

echo ""
echo "📊 Consultando movimientos después de las operaciones..."
make_request "http://localhost:8080/movimientos" "GET" "" "Lista de movimientos"

echo ""
echo "✅ Prueba completada. Revisa los logs de ms-cliente-persona para ver las notificaciones Kafka."
echo "   Para ver los logs en tiempo real ejecuta:"
echo "   docker logs -f ms-cliente-persona"
