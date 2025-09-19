FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim
WORKDIR /app

COPY --from=builder /app/target/co2-calculator.jar target/co2-calculator.jar
COPY co2-calculator /app

LABEL maintainer="Rizwan Mushtaq <rizwan@example.com>"
LABEL description="CO2 Calculator CLI"

