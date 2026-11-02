
# ==========================================
# ETAPA 1: Compilar Spring Boot con Maven
# ==========================================
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

# Instalar fuentes Liberation Sans
RUN apt-get update && \
    apt-get install -y --no-install-recommends fonts-liberation && \
    mkdir -p src/main/resources/fonts/arial && \
    FONT_FILE="$(find /usr/share/fonts/truetype -name 'LiberationSans-Regular.ttf' -print -quit)" && \
    test -n "$FONT_FILE" && \
    FONT_DIR="$(dirname "$FONT_FILE")" && \
    cp "$FONT_DIR/LiberationSans-Regular.ttf" src/main/resources/fonts/arial/ && \
    cp "$FONT_DIR/LiberationSans-Bold.ttf" src/main/resources/fonts/arial/ && \
    cp "$FONT_DIR/LiberationSans-Italic.ttf" src/main/resources/fonts/arial/ && \
    cp "$FONT_DIR/LiberationSans-BoldItalic.ttf" src/main/resources/fonts/arial/ && \
    rm -rf /var/lib/apt/lists/*

# Compilar proyecto
RUN mvn -B -DskipTests clean package


# ==========================================
# ETAPA 2: Ejecutar Spring Boot
# ==========================================
FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /app/target/estudiante-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Djava.awt.headless=true", "-jar", "app.jar"]
