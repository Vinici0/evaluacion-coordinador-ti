# Etapa 1: Build
FROM eclipse-temurin:17-jdk-alpine AS build

# Instalar Maven
RUN apk add --no-cache maven

# Crear directorio de trabajo
WORKDIR /app

# Copiar archivos de configuración de Maven
COPY pom.xml .

# Verificar si existe .mvn y copiarlo si está presente
COPY .mvn* .mvn/
COPY mvnw* ./

# Hacer mvnw ejecutable si existe
RUN if [ -f ./mvnw ]; then chmod +x ./mvnw; fi

# Descargar dependencias (cache layer)
RUN if [ -f ./mvnw ]; then ./mvnw dependency:go-offline -B; else mvn dependency:go-offline -B; fi

# Copiar código fuente
COPY src ./src

# Construir la aplicación
RUN if [ -f ./mvnw ]; then ./mvnw clean package -DskipTests; else mvn clean package -DskipTests; fi

# Etapa 2: Runtime
FROM eclipse-temurin:17-jre-alpine

# Crear usuario no-root por seguridad
RUN addgroup --system spring && adduser --system spring --ingroup spring

# Crear directorio de trabajo
WORKDIR /app

# Copiar el JAR construido desde la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Cambiar propiedad del archivo al usuario spring
RUN chown spring:spring app.jar

# Cambiar al usuario no-root
USER spring:spring

# Configurar variables de entorno por defecto para ms-rastreador
ENV JAVA_OPTS="-Xmx512m -Xms256m"
ENV SPRING_PROFILES_ACTIVE=docker
ENV SPRING_APPLICATION_NAME=ms-rastreador
ENV SERVER_PORT=8084
ENV EUREKA_SERVER_URL=http://ms-eurekaserver:8761/eureka/
ENV EUREKA_REGISTER_WITH_EUREKA=true
ENV EUREKA_FETCH_REGISTRY=true
ENV EUREKA_PREFER_IP_ADDRESS=true
ENV EUREKA_INSTANCE_HOSTNAME=ms-rastreador
ENV LOGGING_LEVEL_EUREKA=DEBUG
ENV LOGGING_LEVEL_SPRING_CLOUD=DEBUG

# Exponer el puerto
EXPOSE 8084

# Configurar health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8084/actuator/health || exit 1

# Comando de inicio
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
