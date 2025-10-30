# Imagen base con Java 17
FROM amazoncorretto:17

# Directorio de trabajo
WORKDIR /app

# Copiar el JAR compilado
COPY target/MovementKardex-0.0.1-SNAPSHOT.jar app.jar

# Comando de inicio (forma shell, compatible con Railway)
ENTRYPOINT java -jar app.jar
