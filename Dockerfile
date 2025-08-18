#DIT IS VOOR RENDER.COM
# Stap 1: De Build-omgeving
# Gebruik een officiële Maven-image met Java 17 om de applicatie te bouwen
FROM maven:3.9-eclipse-temurin-17-focal AS build

# Zet de werkdirectory in de container
WORKDIR /app

# Kopieer de Maven-projectbestanden
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Download de dependencies
RUN mvn dependency:go-offline

# Kopieer de rest van de broncode
COPY src ./src

# Bouw de applicatie. De tests worden overgeslagen.
RUN mvn package -DskipTests


# Stap 2: De Runtime-omgeving
# Gebruik een slanke Java 17 runtime-image om de applicatie uit te voeren
FROM eclipse-temurin:17-jre-jammy

# Zet de werkdirectory
WORKDIR /app

# Kopieer het gebouwde .jar-bestand uit de build-fase
COPY --from=build /app/target/Backend-Dico-0.0.1-SNAPSHOT.jar app.jar

# Stel de poort bloot die de applicatie zal gebruiken
EXPOSE 8000

# Commando om de applicatie te starten
ENTRYPOINT ["java", "-jar", "app.jar"]