ARG ARTIFACT=app.jar
FROM maven:4.0.0-rc-5-eclipse-temurin-25 AS builder
WORKDIR /app

COPY . /app
RUN mvn -B -f pom.xml dependency:go-offline
RUN mvn clean install -DskipTests

FROM eclipse-temurin:25
WORKDIR /app
ARG ARTIFACT

COPY --from=builder /app/target/${ARTIFACT} /app/app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
