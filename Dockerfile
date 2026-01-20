# Paso 1: Construir la aplicación usando Gradle
FROM eclipse-temurin:21-jdk AS build
COPY . .
RUN chmod +x gradlew
RUN ./gradlew build -x test

# Paso 2: Ejecutar la aplicación
FROM eclipse-temurin:21-jre
COPY --from=build /build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]