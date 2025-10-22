# Gebruik een basis-image met Java 21
FROM eclipse-temurin:21-jre-jammy

# Maak een werkdirectory aan in de container
WORKDIR /app

# Kopieer het JAR-bestand naar de container en hernoem het naar app.jar
COPY backenddico-0.0.1-SNAPSHOT.jar app.jar

# Stel de poort bloot die de applicatie zal gebruiken
EXPOSE 8080

# Het commando om de applicatie te starten wanneer de container wordt gestart
ENTRYPOINT ["java", "-jar", "app.jar"]