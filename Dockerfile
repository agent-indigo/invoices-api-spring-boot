FROM eclipse-temurin:17
COPY target/*.jar invoices-api.jar
EXPOSE 8080
CMD ["java", "-jar", "invoices-api.jar"]