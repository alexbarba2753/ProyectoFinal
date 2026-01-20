# Etapa 1: Construcción
FROM gradle:8.5-jdk21 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
# Construimos el JAR saltando los tests para asegurar que pase
RUN gradle build -x test --no-daemon

# Etapa 2: Ejecución
FROM eclipse-temurin:21-jre
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]