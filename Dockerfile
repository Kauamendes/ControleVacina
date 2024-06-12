FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/demo-1.0.0.jar deploy_controleVacina-1.0.0.jar
EXPOSE 8080
CMD ["java", "-jar", "deploy_controleVacina-1.0.0.jar"]