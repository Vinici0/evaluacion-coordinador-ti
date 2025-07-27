# Etapa 1: Build
FROM eclipse-temurin:21-jdk-alpine AS build

# Instalar Maven
RUN apk add --no-cache maven

# Crear directorio de trabajo
WORKDIR /app

# Copiar archivos de configuración de Maven
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
COPY mvnw.cmd .

# Hacer mvnw ejecutable
RUN chmod +x ./mvnw

# Descargar dependencias (cache layer)
RUN ./mvnw dependency:go-offline -B

# Copiar código fuente
COPY src ./src

# Construir la aplicación
RUN ./mvnw clean package -DskipTests

# Etapa 2: Runtime
FROM eclipse-temurin:21-jre-alpine

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

# Configurar variables de entorno por defecto para Eureka Server
ENV JAVA_OPTS="-Xmx512m -Xms256m"
ENV SPRING_PROFILES_ACTIVE=docker
ENV SPRING_APPLICATION_NAME=ms-eurekaserver
ENV SERVER_PORT=8761
ENV EUREKA_SERVER_URL=http://localhost:8761/eureka/
ENV EUREKA_REGISTER_WITH_EUREKA=false
ENV EUREKA_FETCH_REGISTRY=false
ENV EUREKA_INSTANCE_HOSTNAME=ms-eurekaserver

# Exponer el puerto estándar de Eureka
EXPOSE 8761

# Configurar health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8761/actuator/health || exit 1

# Comando de inicio
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
