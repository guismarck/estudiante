
# ==========================================
# ETAPA 1: Compilar Spring Boot con Maven
# ==========================================
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn -B -DskipTests clean package


# ==========================================
# ETAPA 2: Ejecutar Spring Boot
# ==========================================
FROM eclipse-temurin:17-jre

WORKDIR /app

# Instalar fuentes y configuración gráfica
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
    fontconfig \
    fonts-dejavu-core && \
    fc-cache -f -v && \
    rm -rf /var/lib/apt/lists/*

COPY --from=build /app/target/estudiante-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Djava.awt.headless=true", "-jar", "app.jar"]
