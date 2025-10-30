# Etapa 1: Construcción del proyecto con Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Imagen ligera para ejecución
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copia el .jar construido desde la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Expone el puerto (opcional, pero recomendado para claridad)
EXPOSE 8080

# Comando de inicio del contenedor
ENTRYPOINT ["java", "-jar", "app.jar"]
