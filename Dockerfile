FROM eclipse-temurin:17-jre-alpine
WORKDIR /usr/src/app
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
COPY gestion-cuentas-application/target/*.jar application.jar
ENTRYPOINT java -jar application.jar
EXPOSE 8880