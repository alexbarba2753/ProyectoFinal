# Etapa 1: Construcción (Build)
FROM gradle:8.10-jdk21 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
# Ejecuta la compilación dentro de Render
RUN gradle build --no-daemon -x test

# Etapa 2: Ejecución (Run)
FROM eclipse-temurin:21-jdk-alpine
# Copiamos el archivo generado en la etapa anterior
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]