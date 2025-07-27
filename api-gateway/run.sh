#!/bin/bash

# Script para ejecutar el API Gateway
# Asegura que se use Java 17

export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64

echo "🚀 Iniciando API Gateway..."
echo "Java Home: $JAVA_HOME"
echo "Puerto: 8080"
echo "============================================="

cd "$(dirname "$0")"

# Compilar y ejecutar
mvn spring-boot:run
